package orderManagement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class OrderManagementMain{
	static Scanner stdIn = new Scanner(System.in);

	public static void main(String [] args){
		while( true ){
			System.out.println( "処理を選択してください" );
			System.out.println( "1 受注検索" );
			System.out.println( "2 受注登録" );
			System.out.println( "3 受注削除" );
			System.out.println( "0 終了" );
			System.out.print( "番号:" );

			String inSessionStr = stdIn.next();
			System.out.println();
			try{
				int Session = Integer.parseInt( inSessionStr );     //String型をint型に変換(全角の数字を拾うため)

				if ( Session == 0 ){                        //終了
					System.exit(0);

				}else if ( Session == 1 ) {                 //検索
					Search();

				}else if ( Session == 2 ) {                 //登録
					entry();

				}else if (Session == 3) {                   //削除
					deletion();

				}else {                                     //上記の数字以外
					System.out.println("入力がデータが不正です\n");
				}

			}catch ( NumberFormatException e ) {             //formatエラー
				System.out.println("入力がデータが不正です\n");
			}
		}
	}


	/*-----　検 索　------*/
	private static void Search() {
		System.out.println( "顧客名の（の一部を入力してください）" );
		System.out.print( "顧客名:" );
		String inCustomName = stdIn.next();
		System.out.println();

		SelectAccess select = new SelectAccess();
		ArrayList <SelectOrderInfo> orderList = select.getOrderList(inCustomName);

		if ( orderList.size() != 0 ){
			for ( SelectOrderInfo order : orderList ){
				System.out.println( "受注No.:" + order.getOrderNumber() );
				System.out.println( "受注受付:" + order.getOrderDate() );
				System.out.println( "顧客名:" + order.getCustomName() + "(" + order.getCustomCode() +")" );
				System.out.println( "担当者名:" + order.getSalesName() + "(" + order.getSalesCode() +")" );
				System.out.println( "受注商品一覧");

				//itemList
				select = new SelectAccess();
				ArrayList<SelectOrderItem> itemList = select.getOrderItemList( order.getOrderNumber() );

				for( SelectOrderItem item : itemList ) {
					System.out.printf( "%-8s%s : %d個\n",
					     item.getItemCode(), item.getItemName() , item.getItemQuantity() );
				}
				System.out.println();
			}
		}else {
			System.out.print("該当する顧客の受注は存在しません");
			System.out.println();
		}

	}



	//------  登 録  -------//
	static void entry(){
		try{
			/* 受注月日の入力項目 */
			int cnt = 1;
			int inYear = 0, inMonth = 0, inDay = 0;
			Calendar cal = Calendar.getInstance();
			
			System.out.print( "受注年を入力してください\n年：" );
			String inYearStr = stdIn.next();
			System.out.print( "受注月を入力してください\n月：" );
			String inMonthStr = stdIn.next();
			System.out.print( "受注日を入力してください\n日：" );
			String inDayStr = stdIn.next();

			inYear = Integer.parseInt( inYearStr );       //String型をint型に変換(全角の数字を拾うため)
			inMonth = Integer.parseInt( inMonthStr );
			inDay = Integer.parseInt( inDayStr );
			
			cal.setLenient(false);
			cal.set(inYear, inMonth-1, inDay);
			cal.getTime().getTime();     //セットされた時間を取得

			if ( inYear > 9999 ){
				System.out.println( "入力データが不正です\n" );
				return;
			}


			/* 顧客入力項目 */
			System.out.println( "顧客を選択してください" );
			SelectAccess select = new SelectAccess();
			ArrayList <SelectCustom> customList = select.getCustomResult();

			for( SelectCustom custom : customList){
				System.out.printf( "%-2d %s", cnt, custom.getCustomName() );
				System.out.printf( "(%s)\n", custom.getCustomCode() );
				cnt++;
			}
			System.out.print( "番号：" );
			String inOrderNumberStr = stdIn.next();

			int inOrderNumber = Integer.parseInt( inOrderNumberStr );       //String型をint型に変換(全角の数字を拾うため)
			inOrderNumber = customList.get( inOrderNumber -1 ).getCustomCode();     //存在しなければ例外


			/* 担当者入力項目 */
			System.out.println( "\n担当者を選択してください") ;
			select = new SelectAccess();
			ArrayList <SelectSales> salesList = select.getSalesResult();
			cnt = 1;
			for( SelectSales sales : salesList){
				System.out.printf( "%-2d %s", cnt, sales.getSalesName() );
				System.out.printf( "(%s)\n", sales.getSalesCode() );
				cnt++;
			}
			System.out.print( "番号：" );
			String inSalesNumberStr = stdIn.next();

			int inSalesNumber = Integer.parseInt( inSalesNumberStr );       //String型をint型に変換(全角の数字を拾うため)
			String inSalesNumberS = salesList.get( inSalesNumber -1 ).getSalesCode();     //存在しなければ例外


			/* 商品入力項目 */
			System.out.println( "\n商品を選択してください" );
			select = new SelectAccess();
			ArrayList <SelectItem> itemList = select.getItemResult();

			/* 商品一覧表示( format ) */
			cnt = 1;
			String pItemName = null;
			for( SelectItem item : itemList ){
				pItemName = item.getItemName();
				
				if( item.getItemName().length() < 14 ){     //14以下まで全角スペースを付与
					for( int i = item.getItemName().length(); i < 14; i++ ){
						pItemName = pItemName  + "　";
					}		
				}
				
				System.out.printf( "%3d ", cnt );          // 連番 商品名 ( 商品コード )
				System.out.printf( "%.14s", pItemName );
				System.out.printf( "(%3s)", item.getItemCode() );
				if( cnt%2 != 0) { 
					System.out.print( "     " );
				}else { 
					System.out.println();
				}
				cnt++;
			}

			/* 商品追加や計算 */
			double totalItemPrice = 0.0;
			double unitItemPrice = 0.0;
			double totalUnitItemPrice = 0.0;
			int itemCode = 0,itemQuantity = 0;
			ArrayList<Integer> itemCodeList = new ArrayList<Integer>();
			ArrayList<Integer> quantityList = new ArrayList<Integer>();

			while(true){
				//商品
				System.out.print( "商品の一連番号(0の入力で選択終了)：" );
				int inItemNumber = stdIn.nextInt();

				if(inItemNumber == 0){
					break;
				}
				if ( inItemNumber > itemList.size() || inItemNumber <= -1 ){
					System.out.print( "入力データが不正です\n" );
					continue;
				}

				//itemCode = SelectAccess.getItemCode( inItemNumber );     //下とどちらでもOK
				itemCode = itemList.get( inItemNumber-1 ).getItemCode();   //( 一連番号から商品コードの抽出 )       
				itemCodeList.add(itemCode);

				//数量
				System.out.print( "数量：" );
				itemQuantity = stdIn.nextInt();
				quantityList.add(itemQuantity);

				unitItemPrice = SelectAccess.getTanka( itemCode );          //( 書品コードから単価の抽出 )
				totalUnitItemPrice = unitItemPrice * itemQuantity;          //単価×数量
				totalItemPrice = totalItemPrice + totalUnitItemPrice;       //合計
			}

			double tax = Math.floor( totalItemPrice * 0.08 );
			double requestMoney = totalItemPrice + tax;

			//Insert( order_titleとorder_detailに登録 )
			InsertAccess.insertOT( cal, inOrderNumber, inSalesNumberS,
					itemCodeList, quantityList, totalItemPrice, tax, requestMoney); 

			System.out.println( "\n受注を登録しました\n" );


		} catch (IllegalArgumentException e) {       //不適切な引数の場合
			System.out.println( "入力データが不正です\n" );
			return;
		} catch (IndexOutOfBoundsException e) {      //インデックスが範囲外の場合
			System.out.println( "入力データが不正です\n" );
			return;
		}
	}



	/*------ 削　除　------*/	
	private static void deletion() {
		System.out.println( "削除する受注No.を入力してください" );
		System.out.print( "受注No.:" );
		String inOrderNum = stdIn.next();

		try{
			int deleteOrderNumber = Integer.parseInt( inOrderNum );
			Boolean data = SelectAccess.DeleteOrderNameShow( deleteOrderNumber );

			if(data == true){
				System.out.println( "\n受注を削除しました\n" );
				DeleteAccess.Delete( deleteOrderNumber );
			}else {
				System.out.println("\n該当する受注No.は存在しません:\n" );
			}

		}catch ( NumberFormatException e ) { 
			System.out.println("入力がデータが不正です\n");
		}
	}

}
