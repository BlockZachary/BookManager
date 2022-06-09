package book.manage;

import book.manage.entity.Book;
import book.manage.entity.Student;
import book.manage.sql.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;

import java.io.*;
import java.util.Scanner;
import java.util.logging.LogManager;

@Log
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            LogManager logManager = LogManager.getLogManager();
            logManager.readConfiguration(Resources.getResourceAsStream("logging.properties"));

            while (true) {
                System.out.println("======================");
                System.out.println("1.录入学生信息");
                System.out.println("2.录入书籍信息");
                System.out.println("3.添加借阅信息");
                System.out.println("4.查询借阅信息");
                System.out.println("5.查询学生信息");
                System.out.println("6.查询书籍信息");
                System.out.print("输入你想要执行的操作(输入其他任意数字退出)：");
                int input;
                try {
                    input = scanner.nextInt();
                } catch (Exception e) {
                    return;
                }
                scanner.nextLine();
                switch (input) {
                    case 1:
                        addStudent(scanner);
                        break;
                    case 2:
                        addBook(scanner);
                        break;
                    case 3:
                        addBorrow(scanner);
                        break;
                    case 4:
                        showBorrow();
                        break;
                    case 5:
                        showStudent();
                        break;
                    case 6:
                        showBook();
                        break;
                    default:
                        return;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("请输入学生名字：");
        String name = scanner.nextLine();
        System.out.print("请输入学生性别：");
        String gender = scanner.nextLine();
        System.out.print("请输入学生年级：");
        int grade = Integer.parseInt(scanner.nextLine());
        Student student = new Student(name, gender, grade);
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addStudent(student);
            if (i > 0) {
                System.out.println("学生信息录入成功！");
                log.info("新添加了一条学生信息：" + student);
            } else {
                System.out.println("学生信息录入失败！请重试！");
            }
        });

    }

    private static void addBook(Scanner scanner) {
        System.out.print("请输入书籍标题：");
        String title = scanner.nextLine();
        System.out.print("请输入书籍描述：");
        String des = scanner.nextLine();
        System.out.print("请输入书籍价格：");
        double price = Double.parseDouble(scanner.nextLine());
        Book book = new Book(title, des, price);
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addBook(book);
            if (i > 0) {
                System.out.println("书籍信息录入成功！");
                log.info("新添加了一条书籍信息：" + book);
            } else {
                System.out.println("书籍信息录入失败！请重试！");
            }
        });

    }

    private static void addBorrow(Scanner scanner) {
        System.out.print("请输入学生号：");
        int sid = Integer.parseInt(scanner.nextLine());
        System.out.print("请输入书籍号：");
        int bid = Integer.parseInt(scanner.nextLine());
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addBorrow(sid, bid);
            if (i>0){
                System.out.println("借阅信息录入成功！");
                log.info("新添加一条借阅信息："+ "学号:"+sid+"--"+"书籍号:"+bid);
            }else {
                System.out.println("借阅信息录入失败！请重试！");
            }
        });
    }

    private static void showBorrow(){
        SqlUtil.doSqlWork(mapper->{
            mapper.getBorrowList().forEach(borrow -> {
                System.out.println(borrow.getStudent().getName()+"->"+borrow.getBook().getTitle());
            });
        });

    }

    private static void showStudent(){
        System.out.println("学号\t姓名\t性别\t年级");
        SqlUtil.doSqlWork(mapper->{
            mapper.getStudentList().forEach(student -> {
                System.out.println(student.getSid()+"\t\t"+student.getName()+"\t"+student.getGender()+"\t\t"+student.getGrade());
            });
        });
    }

    private static void showBook(){
        System.out.println("书号\t书名\t描述\t价格");
        SqlUtil.doSqlWork(mapper->{
            mapper.getBookList().forEach(book -> {
                System.out.println(book.getBid()+"--"+book.getTitle()+"--"+book.getDes()+"--"+book.getPrice());
            });
        });
    }
}
