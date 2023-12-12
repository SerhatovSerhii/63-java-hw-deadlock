package ait.homework.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

    public class DeadlockExample {

        static class Resource {
            private final Lock lock = new ReentrantLock();

            public void acquire() {
                lock.lock();
            }

            public void release() {
                lock.unlock();
            }
        }

        static class Process1 extends Thread {
            private final Resource resource1;
            private final Resource resource2;

            public Process1(Resource r1, Resource r2) {
                this.resource1 = r1;
                this.resource2 = r2;
            }

            @Override
            public void run() {
                while (true) {
                    resource1.acquire();
                    System.out.println("Process 1 acquired resource 1");
                    try {
                        Thread.sleep(100); // Добавляем задержку для увеличения вероятности deadlock
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    resource2.acquire();
                    System.out.println("Process 1 acquired resource 2");

                    // Do some work

                    resource2.release();
                    System.out.println("Process 1 released resource 2");
                    resource1.release();
                    System.out.println("Process 1 released resource 1");
                }
            }
        }

        static class Process2 extends Thread {
            private final Resource resource1;
            private final Resource resource2;

            public Process2(Resource r1, Resource r2) {
                this.resource1 = r1;
                this.resource2 = r2;
            }

            @Override
            public void run() {
                while (true) {
                    resource2.acquire();
                    System.out.println("Process 2 acquired resource 2");
                    resource1.acquire();
                    System.out.println("Process 2 acquired resource 1");

                    // Do some work

                    resource1.release();
                    System.out.println("Process 2 released resource 1");
                    resource2.release();
                    System.out.println("Process 2 released resource 2");
                }
            }
        }

        public static void main(String[] args) {
            Resource resource1 = new Resource();
            Resource resource2 = new Resource();

            Process1 process1 = new Process1(resource1, resource2);
            Process2 process2 = new Process2(resource1, resource2);

            process1.start();
            process2.start();
        }
    }


