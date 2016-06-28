package com.chowen.lightutils.message;

import com.tongmo.kk.lib.log.L;
import com.tongmo.kk.lib.task.TaskExecutor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: neevek
 * Date: 7/20/13
 * Time: 14:50 AM
 */
public class MessagePump extends Thread {
    private static MessagePump sInstance;

    private PriorityBlockingQueue<Message> mMessagePump;
    private List<List<WeakReference<MessageCallback>>> mMessageAndObserverList;

    public static MessagePump getInstance() {
        if (sInstance == null) {
            synchronized (MessagePump.class) {
                if (sInstance == null) {
                    sInstance = new MessagePump();
                }
            }
        }

        return sInstance;
    }

    private MessagePump() {
        setName("MessagePump");
        mMessagePump = new PriorityBlockingQueue<Message>(100, new Comparator<Message>() {
            public int compare(Message o1, Message o2) {
                return o1.priority < o2.priority ? -1 : o1.priority > o2.priority ? 1 : 1;
            }
        });
        mMessageAndObserverList = new ArrayList<List<WeakReference<MessageCallback>>>(
            Collections.<List<WeakReference<MessageCallback>>>nCopies(Message.Type.values().length, null));
        // start the background thread to process messages.
        start();

        if (L.DEBUG) L.d("started MessagePump");
    }

    public void destroyMessagePump() {
        if (L.DEBUG) L.d("start destroying MessagePump");

        // this message is used to destroy the message center,
        // we use the "Poison Pill Shutdown" approach, see: http://stackoverflow.com/a/812362/668963
        broadcastMessage(Message.Type.DESTROY_MESSAGE_PUMP, null, Message.PRIORITY_EXTREMELY_HIGH);
        sInstance = null;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(MIN_PRIORITY);
        dispatchMessages();

        if (L.DEBUG) L.d("destroyed MessagePump");
    }

    public synchronized void register(Message.Type messageType, MessageCallback callback) {
        List<WeakReference<MessageCallback>> observerList = mMessageAndObserverList.get(messageType.ordinal());

        if (observerList == null) {
            observerList = new ArrayList<WeakReference<MessageCallback>>();
            mMessageAndObserverList.set(messageType.ordinal(), observerList);
        }

        if (indexOf(callback, observerList) == -1)
            observerList.add(new WeakReference<MessageCallback>(callback));
    }

    private int indexOf(MessageCallback callback, List<WeakReference<MessageCallback>> observerList) {
        try {
            for (int i = 0; i < observerList.size(); ++i) {
                if (observerList.get(i).get() == callback)
                    return i;
            }

        } catch (Exception e) {
            // ignore the exception
            // the observerList may be modified from within dispatchMessages() method,
            // we should catch all exceptions in case observerList is not in a right
            // state in terms item count.
        }

        return -1;
    }

    public synchronized void unregister(Message.Type messageType, MessageCallback callback) {
        List<WeakReference<MessageCallback>> observerList = mMessageAndObserverList.get(messageType.ordinal());

        if (observerList != null) {
            int index = indexOf(callback, observerList);

            if (index != -1) {
                observerList.remove(index);
            }
        }
    }


    public synchronized void unregister(MessageCallback callback) {
        Message.Type[] types = Message.Type.values();

        for (int i = 0; i < types.length; ++i) {
            unregister(types[i], callback);
        }
    }

    public void broadcastMessage(Message.Type messageType, Object data) {
        mMessagePump.put(Message.obtainMessage(messageType, data, Message.PRIORITY_NORMAL, null));
    }

    public void broadcastMessage(Message.Type messageType, Object data, int priority) {
        mMessagePump.put(Message.obtainMessage(messageType, data, priority, null));
    }

    public void broadcastMessage(Message.Type messageType, Object data, int priority, Object sender) {
        mMessagePump.put(Message.obtainMessage(messageType, data, priority, sender));
    }

    private void dispatchMessages() {
        while (true) {
            try {
                final Message message = mMessagePump.take();

                if (message.type == Message.Type.DESTROY_MESSAGE_PUMP)
                    break;

                final List<WeakReference<MessageCallback>> observerList = mMessageAndObserverList.get(message.type.ordinal());

                if (observerList != null && observerList.size() > 0) {
                    message.referenceCount = observerList.size();

                    for (int i = 0; i < observerList.size(); ++i) {
                        final MessageCallback callback = observerList.get(i).get();

                        if (callback == null) {
                            observerList.remove(i);
                            --i;

                            if (--message.referenceCount == 0) {
                                message.recycle();
                            }

                        } else {
                            TaskExecutor.runTaskOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (L.DEBUG) {
                                        callback.onReceiveMessage(message);

                                    } else {
                                        try {
                                            // call the target on the UI thread
                                            callback.onReceiveMessage(message);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    // recycle the Message object
                                    if (--message.referenceCount == 0) {
                                        message.recycle();
                                    }
                                }
                            });
                        }
                    }

                } else {
                    message.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}