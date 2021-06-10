package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

// TO DO
public class StackAsQueue<T> {

  Stack<T> head = new Stack<>();
  Stack<T> tail = new Stack<>();


  public T dequeueFlipFlop() {
    return head.pop();
  }
  public void enqueueSplitQueue(T elem) {
    tail.push(elem);
  }
  public boolean isEmpty() {
    return head.isEmpty() && tail.isEmpty();
  }
}

