package cn.ch2.forkjoin.fileread;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * <p>
 * 使用ForkJoin异步读取磁盘文件
 * </p>
 * 采用异步
 * 注意这里的异步就是对main线程的异步
 * 不会对ForkJoin里的多线程异步,里面的线程本身就是并行处理的
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-26
 */
public class FileReadPathMain {


    //读取文件任务对象
    static class FileTask extends RecursiveAction {

        private File file;

        public FileTask(File file) {
            this.file = file;
        }

        @Override
        protected void compute() {

            List<FileTask> fileTasks=new LinkedList<>();

            File[] files=file.listFiles();
            if(files!=null){
                for (File file:files){
                    //如果内部文件还是目录再次进行递归读取
                    if(file.isDirectory()){
                        fileTasks.add(new FileTask(file));
                    }else{
                        System.out.println(file.getAbsolutePath());
                    }
                }
                //递归读取目录中的文件,把任务丢进去执行,然后这里进行使用join进行等待完成
                for (FileTask fileTask:invokeAll(fileTasks)){
                    fileTask.join();
                }
            }

        }

    }


    public static void main(String[] args) {

        ForkJoinPool forkJoinPool=new ForkJoinPool();
        FileTask fileTask=new FileTask(new File("E:/"));
        //采用异步提交让主线程可以做其他的事情
        forkJoinPool.execute(fileTask);

        System.out.println("主线程还可以处理其他的事件");
        for (int i = 0; i < 3; ++i) {
            System.out.println("主线程吃了"+(i+1)+"碗饭");
        }

        //进入阻塞
        fileTask.join();
        System.out.println("所以线程执行完毕!");

    }


}
