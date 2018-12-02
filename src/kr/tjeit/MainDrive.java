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
	
	
//	배열은 크기 제한때문에 사용하지 않을 예정
//	static Person[] peopleArray = new Person[100];

// ArrayList를 활용해보자
	
	static List<Person> peopleList = new ArrayList<Person>();
	
	
	
	public static void main(String[] args) {

//		0을 누르기 전까지는 반복적으로 메뉴를 출력

		printMenu();

		
	}

	static void printMenu() {
		
		
		
		

		Scanner scan = new Scanner(System.in);
		int userInput = 0;

//		사용자가 0번을 눌러서 종료하기 전까지는 프로그램이 계속 유지.

		while (true) {
//			단순 메뉴 출력

			System.out.println("****메뉴 목록****");
			System.out.println("1.전화번호 추가");
			System.out.println("2.전화번호 목록 조회");
			System.out.println("3.총 인원수 확인");
			System.out.println("0.프로그램 종료");
			System.out.println("===============");
			System.out.print("번호를 입력하세요:");

//			메뉴 선택지 입력

			userInput = scan.nextInt();

//			0을 입력했음 => 종료됨  => while 문 탈출! => 자동 종료
//			0을 안눌렀다면? 프로그램이 계속 켜져있게 됨.
			if (userInput == 0) {

				System.out.println("종료됩니다");
				break;
			} else if (userInput == 1) {

//				전화번호 추가 메쏘드를 콜.
				addPhoneNum();

			} else if (userInput == 2) {
//				전화번호 목록 조회 코드 작성
				
				printPhoneNumList();

			}else if (userInput ==3) {
//				전화번호 부에 몇명이 저장되어 있는지? 메쏘드 콜.
				
				showPeopleCount();
				
				
				
				
				
			} 
			
			else {

				System.out.println("잘못된 입력입니다.");

			}

		}

	}

//	실제로 파일에 연락처 정보를 저장하는 메쏘드.
	static void addPhoneNum() {

		Scanner scanner = new Scanner(System.in);
//		정보를 입력받아 텍스트 파일에 추가 

		System.out.println("이름을 입력해 주세요  :");
		String name = scanner.nextLine();

		System.out.println("전화번호 입력해 주세요");
		String PhoneNum = scanner.nextLine();

		System.out.println("이름 :" + name);
		System.out.println("전화번호 :" + PhoneNum);

//		파일 출력 부분 작성 시작. 
		File f = new File("C:/temp/phonebook.csv");

//		자동생성. (이클립스 제공기능 => FileWriter 작업시)

		try {

//			파일 출력 담당. true => 내용물 이어붙이기. 
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);

//			bw이용해서 기록할 사항: 조경진.01051123237,2018-12-02 오후 12시 07분
//			이름과 연락처는 이미 입력받은 상태
//			등록 일시만 자동으로 따내기 

//          현재시간 저장
			Calendar cal = Calendar.getInstance();

//			2018-12-02 오후 12시 :25 양식을 미리 지정.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a h:mm");

//			만들어둔 양식을 이용해 String으로 변환
			String dateStr = sdf.format(cal.getTime());

			System.out.println(dateStr);

//			이름 , 연락처, 일시 묶어서 bw를 이용해 파일에 기록
			bw.write(name + "," + PhoneNum + "," + dateStr);

//			줄바꿈을 위해 수동으로 한줄 강제로 내림.
			bw.newLine();

			bw.close();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	static void printPhoneNumList () {
//		사용자에게 추가로 입력받을 필요가 x
//		파일을 열고 그 안에 들어있는 모든 내용을 출력.
//		파일을 읽는 FileReader , BufferedReader 
		
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
//				읽어온 line 가지고 가공해서 person 클래스로 추가.
//				people 배열에 하나하나 대입.
				
				String[] infos = line.split(",");
				Person p= new Person();
				p.setName(infos[0]);
				p.setPhoneNum(infos[1]);
				
				
				
//				infos[2] => 캘린더 변수로 변환.
				SimpleDateFormat parseSdf = new SimpleDateFormat("yyyy-MM-dd a h:mm");
				Calendar c = Calendar.getInstance();
				
				c.setTime(parseSdf.parse(infos[2]));
				
				p.setCreatedAt(c);
				
//				ArrayList 에 만들어진 사람 데이터를 추가.
				
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
		System.out.println("전화번호부에 총 " + peopleCount+ "명이 등록되었습니다");
		
		
		
	}
	
	
	
}
