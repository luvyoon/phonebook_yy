package kr.tjeit;

import java.awt.PageAttributes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.acl.Permission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

import kr.tjeit.datas.Person;

public class MainDrive {
	
	
//	�迭�� ũ�� ���Ѷ����� ������� ���� ����
//	static Person[] peopleArray = new Person[100];

// ArrayList�� Ȱ���غ���
	
	static List<Person> peopleList = new ArrayList<Person>();
	
	
	
	public static void main(String[] args) {

//		0�� ������ �������� �ݺ������� �޴��� ���

		printMenu();

		
	}

	static void printMenu() {
		
		
		
		

		Scanner scan = new Scanner(System.in);
		int userInput = 0;

//		����ڰ� 0���� ������ �����ϱ� �������� ���α׷��� ��� ����.

		while (true) {
//			�ܼ� �޴� ���

			System.out.println("****�޴� ���****");
			System.out.println("1.��ȭ��ȣ �߰�");
			System.out.println("2.��ȭ��ȣ ��� ��ȸ");
			System.out.println("3.�� �ο��� Ȯ��");
			System.out.println("0.���α׷� ����");
			System.out.println("===============");
			System.out.print("��ȣ�� �Է��ϼ���:");

//			�޴� ������ �Է�

			userInput = scan.nextInt();

//			0�� �Է����� => �����  => while �� Ż��! => �ڵ� ����
//			0�� �ȴ����ٸ�? ���α׷��� ��� �����ְ� ��.
			if (userInput == 0) {

				System.out.println("����˴ϴ�");
				break;
			} else if (userInput == 1) {

//				��ȭ��ȣ �߰� �޽�带 ��.
				addPhoneNum();

			} else if (userInput == 2) {
//				��ȭ��ȣ ��� ��ȸ �ڵ� �ۼ�
				
				printPhoneNumList();

			}else if (userInput ==3) {
//				��ȭ��ȣ �ο� ����� ����Ǿ� �ִ���? �޽�� ��.
				
				showPeopleCount();
				
				
				
				
				
			} 
			
			else {

				System.out.println("�߸��� �Է��Դϴ�.");

			}

		}

	}

//	������ ���Ͽ� ����ó ������ �����ϴ� �޽��.
	static void addPhoneNum() {

		Scanner scanner = new Scanner(System.in);
//		������ �Է¹޾� �ؽ�Ʈ ���Ͽ� �߰� 

		System.out.println("�̸��� �Է��� �ּ���  :");
		String name = scanner.nextLine();

		System.out.println("��ȭ��ȣ �Է��� �ּ���");
		String PhoneNum = scanner.nextLine();

		System.out.println("�̸� :" + name);
		System.out.println("��ȭ��ȣ :" + PhoneNum);

//		���� ��� �κ� �ۼ� ����. 
		File f = new File("C:/temp/phonebook.csv");

//		�ڵ�����. (��Ŭ���� ������� => FileWriter �۾���)

		try {

//			���� ��� ���. true => ���빰 �̾���̱�. 
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);

//			bw�̿��ؼ� ����� ����: ������.01051123237,2018-12-02 ���� 12�� 07��
//			�̸��� ����ó�� �̹� �Է¹��� ����
//			��� �Ͻø� �ڵ����� ������ 

//          ����ð� ����
			Calendar cal = Calendar.getInstance();

//			2018-12-02 ���� 12�� :25 ����� �̸� ����.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a h:mm");

//			������ ����� �̿��� String���� ��ȯ
			String dateStr = sdf.format(cal.getTime());

			System.out.println(dateStr);

//			�̸� , ����ó, �Ͻ� ��� bw�� �̿��� ���Ͽ� ���
			bw.write(name + "," + PhoneNum + "," + dateStr);

//			�ٹٲ��� ���� �������� ���� ������ ����.
			bw.newLine();

			bw.close();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	static void printPhoneNumList () {
//		����ڿ��� �߰��� �Է¹��� �ʿ䰡 x
//		������ ���� �� �ȿ� ����ִ� ��� ������ ���.
//		������ �д� FileReader , BufferedReader 
		
		File file= new File("C:/temp/phonebook.csv");
		
		peopleList.clear();
		try {
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			
			int index=0;
			
			while (true) {
				String line = br.readLine();
				if(line == null) {
					
					break;
				}
//				�о�� line ������ �����ؼ� person Ŭ������ �߰�.
//				people �迭�� �ϳ��ϳ� ����.
				
				String[] infos = line.split(",");
				Person p= new Person();
				p.setName(infos[0]);
				p.setPhoneNum(infos[1]);
				
				
				
//				infos[2] => Ķ���� ������ ��ȯ.
				SimpleDateFormat parseSdf = new SimpleDateFormat("yyyy-MM-dd a h:mm");
				Calendar c = Calendar.getInstance();
				
				c.setTime(parseSdf.parse(infos[2]));
				
				p.setCreatedAt(c);
				
//				ArrayList �� ������� ��� �����͸� �߰�.
				
				peopleList.add(p);
				
				
				p.printMyInfo();
				
				
				
				

//				System.out.println(line);
			}
			
			br.close();
			fr.close();

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	static void showPeopleCount() {
		
		int peopleCount = peopleList.size();
		System.out.println("��ȭ��ȣ�ο� �� " + peopleCount+ "���� ��ϵǾ����ϴ�");
		
		
		
	}
	
	
	
}
