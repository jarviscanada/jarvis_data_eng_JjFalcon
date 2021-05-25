package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class StackAsQueue<T> {

  Stack<T> head = new Stack<>();
  Stack<T> tail = new Stack<>();


  public void enqueueFlipFlop(T elem) {
    Stack<T> flipped = new Stack<>();
    if (head.isEmpty()) {
      head.push(elem);
    } else {
      while (!head.isEmpty()) {
        flipped.push(head.pop());
      }
      head.push(elem);
      while (!flipped.isEmpty()) {
        head.push(flipped.pop());
      }
    }
  }

  public T dequeueFlipFlop() {
    return head.pop();
  }


  public void enqueueSplitQueue(T elem) {
    tail.push(elem);
  }


  public T dequeueSplitQueue() {
    if (head.isEmpty()) {
      refill();
    }
    return head.pop();
  }

  private void refill() {
    if (tail.isEmpty()) {
      throw new IndexOutOfBoundsException();
    } else {
      while (!tail.isEmpty()) {
        head.push(tail.pop());
      }
    }
  }

  public T peekSplitQueue() {
    if (head.isEmpty()) {
      refill();
    }
    return head.peek();
  }

  public boolean isEmpty() {
    return head.isEmpty() && tail.isEmpty();
  }
}

