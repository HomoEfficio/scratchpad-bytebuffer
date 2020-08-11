# ByteBuffer Memory Release Test

## Build and Run

Open a terminal for this app.

1. `./gradlew compileJava`
1. `cd build/classes/java/main`
1. `java io.homo_efficio.bytebuffer.ByteBufferRunner`

## Native Memory Monitoring

Open other terminal for Native Memory Monitoring

1. `jps`
1. find the PID of ByteBufferRunner app
1. `jcmd <<PID>> VM.native_memory baseline`
1. `jcmd <<pid>> VM.native_memory summary.diff`
1. We will focus on the `Internal` section of the summary.

example:

```
tmp 🍺🦑🍺🍕🍺 ❯ jps
97121 
94291 GradleDaemon
98691 ByteBufferRunner
97778 Main
98733 Jps
tmp 🍺🦑🍺🍕🍺 ❯ jcmd 98691 VM.native_memory baseline
98691:
Baseline succeeded
tmp 🍺🦑🍺🍕🍺 ❯ jcmd 98691 VM.native_memory summary.diff
98691:

Native Memory Tracking:

Total: reserved=5724003KB -269KB, committed=484387KB +115KB

-                 Java Heap (reserved=4194304KB, committed=262144KB)
                            (mmap: reserved=4194304KB, committed=262144KB)
 
-                     Class (reserved=1070322KB +1KB, committed=21362KB +257KB)
                            (classes #1648)
                            (malloc=11506KB +1KB #1628 +37)
                            (mmap: reserved=1058816KB, committed=9856KB +256KB)
 
-                    Thread (reserved=27761KB, committed=27761KB)
                            (thread #28)
                            (stack: reserved=27648KB, committed=27648KB)
                            (malloc=82KB #162)
                            (arena=32KB #50)
 
-                      Code (reserved=250222KB +18KB, committed=4522KB +146KB)
                            (malloc=622KB +18KB #1334 +20)
                            (mmap: reserved=249600KB, committed=3900KB +128KB)
 
-                        GC (reserved=165935KB, committed=153139KB)
                            (malloc=12691KB #194)
                            (mmap: reserved=153244KB, committed=140448KB)
 
-                  Compiler (reserved=135KB, committed=135KB)
                            (malloc=4KB #51 +2)
                            (arena=131KB #7)
 
-                  Internal (reserved=11741KB, committed=11741KB)
                            (malloc=11709KB #3066)
                            (mmap: reserved=32KB, committed=32KB)
 
-                    Symbol (reserved=2791KB, committed=2791KB)
                            (malloc=1504KB #513)
                            (arena=1287KB #1)
 
-    Native Memory Tracking (reserved=116KB +1KB, committed=116KB +1KB)
                            (malloc=5KB #54)
                            (tracking overhead=111KB +1KB)
 
-               Arena Chunk (reserved=677KB -289KB, committed=677KB -289KB)
                            (malloc=677KB -289KB)
```

## Use ByteBuffer

1. Go to the app terminal, and type any char and ENTER.

1. Then the app will write some texts to files using ByteBuffer, and sleep for 15s.

1. Go quickly to the Native Memory Monitor terminal.
 
## Monitor Native Memory

1. In the Native Memory Monitory, execute `jcmd <<PID>> VM.native_memory summary.diff` quickly.

1. Check the Internal section, and you should see the diff, for example `+87057KB` like below, which means that `87057KB` of Native Memory is used.

```
-                  Internal (reserved=98798KB +87057KB, committed=98798KB +87057KB)
                            (malloc=98766KB +87057KB #3132 +66)
                            (mmap: reserved=32KB, committed=32KB)
```

1. Repeat `jcmd <<PID>> VM.native_memory summary.diff` for over 20s. You may see the diff gets decreased by about 50M, if you are lucky. ;)


## Force GC

You can force GC using VisualVM.

![Imgur](https://i.imgur.com/Reu5fwL.png)

After the GC, do `jcmd <<PID>> VM.native_memory summary.diff` again and check the Internal section.

You SHOULD see the diff decreased by about 85M.

example:

```
-                  Internal (reserved=11752KB +11KB, committed=11752KB +11KB)
                            (malloc=11720KB +11KB #3111 +45)
                            (mmap: reserved=32KB, committed=32KB)
```

## Terminate the App

Go to the app terminal, just type any char and ENTER.

