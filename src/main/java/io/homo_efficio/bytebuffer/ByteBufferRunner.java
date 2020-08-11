package io.homo_efficio.bytebuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-08-09
 */
public class ByteBufferRunner {

    public static void main(String[] args) throws IOException {

        Scanner scanner1 = new Scanner(System.in);
        String input1 = scanner1.nextLine();

        new Thread(() -> {
            System.out.println("--- in thread: " + Thread.currentThread().getName());

            int capacity = 100 * 1024 * 1024;

            writeContents(capacity, "111", "bytebuffer_test_1");
            writeContents(capacity, "222", "bytebuffer_test_2");

            sleep(10000L);

            System.out.println("--- in thread: " + Thread.currentThread().getName());
        }).start();


        new Thread(() -> {
            System.out.println("--- in thread: " + Thread.currentThread().getName());

            int capacity = 70 * 1024 * 1024;

            writeContents(capacity, "333", "bytebuffer_test_3");

            sleep(15000L);

            System.out.println("--- in thread: " + Thread.currentThread().getName());
        }).start();

        Scanner scanner2 = new Scanner(System.in);
        String input2 = scanner2.nextLine();
    }

    private static void sleep(long waitMs) {
        try {
            System.out.println(waitMs + "ms 동안 대기");
            Thread.sleep(waitMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writeContents(int capacity, String contents, String targetFileName) {
        MyByteBuffer myByteBuffer = new MyByteBuffer();
        ByteBuffer byteBuffer = myByteBuffer.newByteBuffer(capacity);
        byte[] bytes = contents.getBytes();
        byteBuffer.put(bytes);

        try {
            myByteBuffer.writeToFile(targetFileName, byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
