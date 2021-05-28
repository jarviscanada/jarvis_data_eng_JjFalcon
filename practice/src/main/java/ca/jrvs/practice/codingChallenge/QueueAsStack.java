package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

// TO DO
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



  public boolean isEmptyTwoQueue() {
    return q1.isEmpty() && q2.isEmpty();
  }

  public boolean isEmptyOneQueue() {
    return q1.isEmpty();
  }
}

