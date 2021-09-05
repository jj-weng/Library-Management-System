/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsjpaclient;

import ejb.session.stateless.BookEntitySessionBeanRemote;
import ejb.session.stateless.LendAndReturnEntitySessionBeanRemote;
import ejb.session.stateless.MemberEntitySessionBeanRemote;
import ejb.session.stateless.RecordEntitySessionBeanRemote;
import ejb.session.stateless.StaffEntitySessionBeanRemote;
import entity.BookEntity;
import entity.LendAndReturnEntity;
import entity.MemberEntity;
import entity.RecordEntity;
import entity.StaffEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.persistence.Query;
import util.exception.BookNotFoundException;
import util.exception.EntityManagerException;
import util.exception.InvalidLoginException;
import util.exception.LendingNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author jiajun
 */
public class Main {

    @EJB(name = "LendAndReturnEntitySessionBeanRemote")
    private static LendAndReturnEntitySessionBeanRemote lendAndReturnEntitySessionBeanRemote;

    @EJB(name = "StaffEntitySessionBeanRemote")
    private static StaffEntitySessionBeanRemote staffEntitySessionBeanRemote;

    @EJB(name = "MemberEntitySessionBeanRemote")
    private static MemberEntitySessionBeanRemote memberEntitySessionBeanRemote;

    @EJB(name = "BookEntitySessionBeanRemote")
    private static BookEntitySessionBeanRemote bookEntitySessionBeanRemote;

    @EJB(name = "RecordEntitySessionBeanRemote")
    private static RecordEntitySessionBeanRemote recordEntitySessionBeanRemote;
    

    private static StaffEntity currentStaffEntity;
    
    
    public static void menu() throws MemberNotFoundException, BookNotFoundException, LendingNotFoundException 
    {   
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while (true)
        {
        System.out.println("*** Welcome to Library Management System (LMS) ***\n");
        System.out.println("1: Login");
        System.out.println("2: Logout\n");
        response = 0;
        
        while (response < 1|| response > 2)
        {
            System.out.print("> ");
            
            response = sc.nextInt();
            
            if (response == 1)
            {
                try
                {
                    login();
                    System.out.println("Login successful!\n");
                    menuMain();
                }
                catch(InvalidLoginException | EntityManagerException ex)
                {
                    System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                }
            }
            else if (response == 2)
            {
                exit();
            }
            else 
            {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        
        if (response == 2) 
        {
            exit();       
        }

        }    
    }
     
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        menu();
    }
    
    
    
    static void operation() throws MemberNotFoundException, BookNotFoundException, LendingNotFoundException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** LMS :: Library Operation ***\n");
        System.out.println("1: Register Member");
        System.out.println("2: Lend Book");
        System.out.println("3: View fine amount");
        System.out.println("4: Return Book");
        System.out.println("5: Back\n");
        
        while(true)
        {
            System.out.print("> ");
            int choice = sc.nextInt();
        if(choice == 1)
            registerMember();
        else if(choice == 2)
            lendBook();
        else if(choice == 3)
            viewFine();
        else if(choice == 4)
            returnBook();
        else if(choice == 5)
            menuMain();
        else 
            System.out.println("Invalid option, please enter the listed commands");
        }
    }
    
    public static void login() throws InvalidLoginException, EntityManagerException 
    {   
        Scanner sc = new Scanner(System.in);
        //String username = "";
        //String password = "";
        
        System.out.println("*** LMS :: Login ***\n");
        System.out.print("Enter username> ");
        String username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();
        
        if (username.length() > 0 && password.length() > 0)
        {
            currentStaffEntity = staffEntitySessionBeanRemote.staffLogin(username, password);
        }
        else 
        {
            throw new InvalidLoginException("Missing login credential!");
        }  
    }
    
    public static void lendBook() throws MemberNotFoundException, BookNotFoundException, LendingNotFoundException 
    {   
        Scanner sc = new Scanner(System.in);
        
        while(true)
        {
            
            System.out.println("*** LMS:: Library Operation :: Lend Book ***\n");
            System.out.print("Enter Book Id> ");
            Long bookId = sc.nextLong();
            sc.nextLine();
            System.out.print("Enter Member Identity Number> ");
            String identityNo = sc.nextLine().trim();
        
            try {
                MemberEntity member = memberEntitySessionBeanRemote.retrieveMemberByIdentity(identityNo);
                BookEntity book = bookEntitySessionBeanRemote.retrieveBookByBookId(bookId);
            
                Long memberId = member.getMemberId();
            
                Date date = new Date();
                
                Boolean isReturnDateNull = lendAndReturnEntitySessionBeanRemote.isReturnDateNull(bookId);
                
                if (isReturnDateNull == false)
                {
                    System.out.println("Book is not available for lending!\n");
                    operation();
                }
                else 
                {
                    LendAndReturnEntity lendAndReturnEntity = new LendAndReturnEntity(memberId, bookId, date, null, new BigDecimal(0), member, book);
                    lendAndReturnEntitySessionBeanRemote.createNewRecord(lendAndReturnEntity);
                    System.out.println("Book: "+ book.getTitle() +" lent to "+ member.getFirstName() +" " + member.getLastName() +".\n");
                    operation();
                }
            }
            catch (BookNotFoundException ex)
            {
                System.out.println(ex);
                operation();
            }
            catch (MemberNotFoundException ex)
            {
                System.out.println(ex);
                operation();
            }
        }
    }

    
    public static void returnBook() throws LendingNotFoundException, MemberNotFoundException, BookNotFoundException
    {
        Scanner sc = new Scanner(System.in);
        
        while (true)
        {
            System.out.println("*** LMS:: Library Operation :: Return Book ***\n");
            System.out.print("Enter Lend Id> ");
            Long lendId = sc.nextLong();
            sc.nextLine();
            System.out.print("Enter fine amount paid> ");
            double finePaid = sc.nextDouble();
            sc.nextLine();
            Date date = new Date();
            
            try 
            {
                LendAndReturnEntity lendAndReturnEntity = lendAndReturnEntitySessionBeanRemote.retrieveLendAndReturnEntityByLendId(lendId);
                Date borrowDate = lendAndReturnEntity.getLendDate();
                Long timeDiff = date.getTime() - borrowDate.getTime();
                Long dayDiff = (timeDiff / 86400000) % 365;
                double fine = 0;
                Long bookId = lendAndReturnEntity.getBookId();
                
                if (dayDiff > 14) 
                {
                    fine = (dayDiff - 14) * 0.5;
                } 
        
                if (finePaid == fine) 
                {
                    lendAndReturnEntity.setReturnDate(date);
                    lendAndReturnEntity.setFineAmount(new BigDecimal(fine));
                    lendAndReturnEntitySessionBeanRemote.update(lendAndReturnEntity);
                    System.out.println("Book: " + lendAndReturnEntity.getBook().getTitle() + " is successfully returned by " + lendAndReturnEntity.getMember().getFirstName() + " " + lendAndReturnEntity.getMember().getLastName() + "\n" );
                    operation();
                }
                else 
                {
                    System.out.println("Unable to return book as wrong fine.\n");
                    operation();
                }
            }
            catch (LendingNotFoundException ex)
            {
                System.out.println("Lend and return for Lend ID does not exist.\n");
                operation();
            }
            catch (NullPointerException ex)
            {
                System.out.println("Lend ID does not exist, please try again!\n");
                operation();
            }
        }
    }
    
    public static void viewFine() throws LendingNotFoundException, MemberNotFoundException, BookNotFoundException 
    {
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("*** LMS:: Library Operation :: View fine amount ***\n");
            System.out.print("Enter Lend Id> ");
            Long lendId = sc.nextLong();
            sc.nextLine();
            double fine = 0;
            Date date = new Date();
            
            try 
            {
                LendAndReturnEntity lendAndReturnEntity = lendAndReturnEntitySessionBeanRemote.retrieveLendAndReturnEntityByLendId(lendId);
                
                Date borrowDate = lendAndReturnEntity.getLendDate();

                Long timeDiff = date.getTime() - borrowDate.getTime();
                Long dayDiff = (timeDiff / 86400000) % 365;
        
                if (dayDiff > 14) 
                {
                    fine = (dayDiff - 14) * 0.5;
                } 

                System.out.println(lendAndReturnEntity.getMember().getFirstName() + " " + lendAndReturnEntity.getMember().getLastName() + " is fined with " + fine + " $.\n");     
                operation();
            }
            catch (LendingNotFoundException ex)
            {
                System.out.println("Lend and return for Lend ID does not exist.\n");
                operation();
            } 
            catch (NullPointerException ex)
            {
                System.out.println("Lend ID does not exist, please try again!\n");
                operation();
            }
        }
    }
    
    public static void menuMain() throws MemberNotFoundException, BookNotFoundException, LendingNotFoundException 
    {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while (true) 
        {
            System.out.println("*** Library Management System (LMS) ***\n");
            System.out.println("You are login as "+ currentStaffEntity.getFirstName() + " " + currentStaffEntity.getLastName() +"\n");
            System.out.println("1: Library Operation");
            System.out.println("2: Logout\n"); 
            response = 0;
            
            while (response < 1 || response > 3)
            {
                System.out.print("> ");
                response = sc.nextInt();
                
                if (response == 1) 
                {
                    try 
                    {
                       operation(); 
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Invalid option, please try again!: " + "\n");
                        operation();
                    }  
                }
                else if (response == 2)
                {
                    menu();
                }
                else 
                {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if (response == 2)
            {
                menu();
            }
        }
    }
    
    public static void registerMember() throws MemberNotFoundException, BookNotFoundException, LendingNotFoundException 
    {
        Scanner sc = new Scanner(System.in);
        try {
            MemberEntity newMemberEntity = new MemberEntity();
            System.out.println("*** LMS :: Library Operation :: Register Member ***\n");
            System.out.print("Enter First Name> ");
            newMemberEntity.setFirstName(sc.nextLine().trim());
            System.out.print("Enter Last Name> ");
            newMemberEntity.setLastName(sc.nextLine().trim());
            System.out.print("Enter Gender> ");
            newMemberEntity.setGender(sc.next().charAt(0));
            System.out.print("Enter Age> ");
            newMemberEntity.setAge(sc.nextInt());
            sc.nextLine();
            System.out.print("Enter Identity Number> ");
            newMemberEntity.setIdentityNo(sc.nextLine().trim());
            System.out.print("Enter Phone> ");
            newMemberEntity.setPhone(sc.nextLine().trim());
            System.out.print("Enter Address> ");
            newMemberEntity.setAddress(sc.nextLine());
        
            memberEntitySessionBeanRemote.createNewRecord(newMemberEntity);
            System.out.println("Member has been registered successfully!\n");
            operation();
        
        } catch (InputMismatchException ex) {
            
            System.out.println("Invalid input, please try again!\n");
            operation();
        }
    } 
    
    public static void exit() 
    {
        System.exit(0);
    }
    
}
