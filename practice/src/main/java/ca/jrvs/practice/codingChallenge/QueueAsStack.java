package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

public class QueueAsStack<T> {

  private Queue<T> q1 = new LinkedList<>();
  private Queue<T> q2 = new LinkedList<>();


  public void pushTwoQueue(T elem) {
    if (q2.isEmpty()) {
      q1.add(elem);
    } else {
      q2.add(elem);
    }
  }

  public T popTwoQueue() {
    int size;
    if (q2.isEmpty()) {
      size = q1.size();
      for (int i = 1; i < size; i++) {
        q2.add(q1.remove());
      }
      return q1.remove();
    } else {
      size = q2.size();
      for (int i = 1; i < size; i++) {
        q1.add(q2.remove());
      }
      return q2.remove();
    }
  }

  public void pushOneQueue(T elem) {
    q1.add(elem);
  }


  public T popOneQueue() {
    for (int i = 1; i < q1.size(); i++) {
      q1.add(q1.remove());
    }
    return q1.remove();
  }


  public T peekSingleQeue() {
    T elem = popOneQueue();
    pushOneQueue(elem);
    return elem;
  }

  public boolean isEmptyTwoQueue() {
    return q1.isEmpty() && q2.isEmpty();
  }

  public boolean isEmptyOneQueue() {
    return q1.isEmpty();
  }
}

