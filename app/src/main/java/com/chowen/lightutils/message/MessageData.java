package com.chowen.lightutils.message;

/**
 * Created with IntelliJ IDEA.
 * User: xiejm
 * Date: 11/13/13
 * Time: 3:14 PM
 */
public class MessageData<T1, T2> {
    public T1 o1;
    public T2 o2;

    public MessageData(T1 o1, T2 o2) {
        this.o1 = o1;
        this.o2 = o2;
    }
}
