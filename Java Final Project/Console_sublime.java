import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

class Main {
   static int adminNumber = 0;

   public static void main(String[] args) throws Exception {
      Main oMain = new Main();
      oMain.Roll();
      System.out.println("Welcome Again");
   }

   void Roll() {
      SuperMarket oMarket = new SuperMarket();
      Scanner getvalue = new Scanner(System.in);
      System.out.println("--------------------------------------");
      System.out.println("|" + "\u001B[32m" + "       SUPERMARKET BILLING SYSTEM   " + "\u001B[0m" + "|");
      System.out.println("--------------------------------------");
      System.out.println();
      System.out.println("Enter 1 for " + "\u001B[34m" + "Admin" + "\u001B[0m");
      System.out.println("Enter 2 for " + "\u001B[34m" + "Customer" + "\u001B[0m");
      adminNumber = getvalue.nextInt();
      oMarket.Mymoney();
      oMarket.Call();
   }
}

class SuperMarket {
   static Scanner scanner = new Scanner(System.in);
   static double money = 0;
   static ArrayList < ArrayList < String >> list = new ArrayList < > ();
   int customer_cash = 0;

   void Mymoney() {
      try {
         String paths = "My_money.txt";
         FileReader inFileReader = new FileReader(paths);
         Scanner scanner = new Scanner(inFileReader);

         if (scanner.hasNextInt()) {
            money = scanner.nextInt();
         } else {
            System.out.println("There is nothing");
         }

         scanner.close();

      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
      }
   }

   void moneyWrite() {
      try {
         FileWriter userFile = new FileWriter("My_money.txt");
         if (Main.adminNumber == 1) {
            System.out.println("Chief Your Money: " + (int) money);
         }

         userFile.write(Integer.toString((int) money));
         userFile.close();
         System.out.println();
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println(e);
      }
   }
   static ArrayList < Integer > singleProductCostlist = new ArrayList < > ();
   static ArrayList < String > productsList = new ArrayList < > ();
   static ArrayList < Integer > productQuantity = new ArrayList < > ();
   static ArrayList < String > StorageName = new ArrayList < > ();
   static ArrayList < Integer > StorageQuantity = new ArrayList < > ();
   int customerCount = 0;
   int adminworks = 0;
   int indexer = 0;

   void Call() {
      Scanner inputScaning = new Scanner(System.in);
      SuperMarket objSuperMarket = new SuperMarket();
      SuperMarketIncome objMarketIncome = new SuperMarketIncome();

      System.out.println();
      System.out.println("Super Market");
      objSuperMarket.Store();
      objSuperMarket.stateStore();

      if (Main.adminNumber == 1) {
         while (adminworks != 6) {
            System.out.println("1.Purchasing Goods from the Vendors");
            System.out.println("2.Add new Products to the Store");
            System.out.println("3.Buy a Product");
            System.out.println("4.Change the Price of the Product");
            System.out.println("5.Market States");
            System.out.println("6.Exit");
            adminworks = inputScaning.nextInt();

            if (adminworks == 1) {
               System.out.println("Chief Your Money : " + money);
               objMarketIncome.adminStorePrint();
               SuperMarketIncome.gudowncheck = 1;
               if (indexer == 0) {
                  objMarketIncome.BuyingGoods();
               }
               indexer++;
               SuperMarketIncome.gudowncheck = 0;
               objMarketIncome.purchasingGoods();
               objMarketIncome.afterPurchase();
               objMarketIncome.adminStorePrint();
               moneyWrite();
            } 
            else if (adminworks == 2) {
               objMarketIncome.adminStorePrint();
               if (indexer == 0) {
                  objMarketIncome.BuyingGoods();
               }
               indexer++;
               objMarketIncome.adminRoll();
               objMarketIncome.adminStorePrint();
               moneyWrite();
            } 
            else if (adminworks == 3) {
               objMarketIncome.adminStorePrint();
               intro();
               moneyWrite();
               objMarketIncome.adminStorePrint();
            } 
            else if (adminworks == 4) {
               objMarketIncome.adminStorePrint();
               valueChange();
               objMarketIncome.adminStorePrint();
            } 
            else if (adminworks == 5) {
               printMonthlyStates();
            }
            else if (adminworks == 6) {
               System.out.println("Thank You");
               return;
            }
         }
      } else if (Main.adminNumber == 2) {
         objMarketIncome.adminStorePrint();
         intro();
         moneyWrite();
         objMarketIncome.adminStorePrint();
         return;
      }
   }

   int totalCost = 0;
   int checkBounty = 0;
   int iterate = 0;
   int checker = 0;

   void intro() {
      SuperMarket oMarket = new SuperMarket();
      System.out.println("Welcome ..,");
      customerCount++;
      System.out.println("Customer: " + customerCount);
      oMarket.checkStorage();
      oMarket.monthlyStates();
      totalCost = 0;
      singleProductCostlist.clear();
      productsList.clear();
      productQuantity.clear();
      iterate = 0;
   }

   void checkStorage() {
      Scanner iScanner = new Scanner(System.in);
      System.out.println("May I help you?");
      System.out.println("Yes or No");
      String yesorno = iScanner.nextLine().toUpperCase().trim();

      int productCheck = 0;

      // yesorno.equalsIgnoreCase("YES")
      while ("YES".equalsIgnoreCase(yesorno)) {
         System.out.println("Tell me, which Grocery You want ?");
         String product = iScanner.nextLine().toUpperCase().trim(); // Product Name Storing
         int productQn = 0; // Product Quantity Storing
         productCheck = 0;
         int user_cost = 0;

         for (int k = 0; k < list.size(); k++) {
            if (list.get(k).contains(product)) {
               productCheck = 1;
               break;
            }
         }

         if (productCheck == 1) {
            productsList.add(product);
            System.out.println("How much quantity you want ?");
            productQn = iScanner.nextInt();
            productQuantity.add(productQn);
            yesorno = "";

            // IMPORTANT LINE
            user_cost = consolidation(productsList.get(iterate), productQuantity.get(iterate));
         } 
         else if (productCheck == 0) {
            System.out.println("Sorry we dont have that product");
            checkStorage();
            return;
         }

         if (checkBounty == 0) {
            printingBills();
         } 
         else if (checkBounty == 1) {
            System.out.println("Sorry : Our Stock is out of Boundry ");
            System.out.println("your current Product cost is: " + user_cost);
            productsList.remove(iterate);
            productQuantity.remove(iterate);
            checkBounty = 0;
            // REMOVING THE OUT OF BOUNTRY PRODUCT FROM THE LIST
         }

         try {
            System.out.println("Do you want any other product ?");
            System.out.println("Yes or No");
            iScanner.nextLine();
            yesorno = iScanner.nextLine().toUpperCase().trim();
         } 
         catch (Exception e) {
            // TODO: handle exception
            System.out.println("To Exception handling Error" + e);
         }

         if (yesorno.equalsIgnoreCase("NO")) {
            returningCustomer();
         } 
         else if (!yesorno.equalsIgnoreCase("yes")) {
            checkStorage(); // calling the same method even if they give wrong answer
         }
         iterate++;

      }

      if (yesorno.equalsIgnoreCase("NO")) {
         System.out.println("Ok Thank You");
         return;
      }

      if (productCheck == 0) {
         checkStorage();
      }
   }

   void returningCustomer() {
      SuperMarketIncome objIncome = new SuperMarketIncome();
      Scanner inputScanner = new Scanner(System.in);

      if (totalCost == 0) {
         System.out.println("Sorry you have not buy anything so thank you welcome");
      } else {
         System.out.println("Your Total Cost is: " + totalCost);
         System.out.println("Your Amount Please ");
         int user_amount = inputScanner.nextInt();

         if (user_amount > totalCost) {
            int addBalance = user_amount - totalCost; // My money Balance
            money += user_amount - addBalance;
            System.out.println("Your Balance " + (user_amount - totalCost));
            CustomerPurchased();
            System.out.println("Thank You ");
            System.out.println("You are Welcome., ");
            objIncome.GudownStockChange();

            if (Main.adminNumber == 1) {
               if ((adminworks != 0) && (adminworks != 3)) {
                  objIncome.GudownStorage();
                  System.out.println("admin work" + adminworks);
                  System.out.println("Gudowncheck" + SuperMarketIncome.gudowncheck);
                  if (SuperMarketIncome.gudowncheck == 1) {
                     objIncome.purchasingGoods();
                     objIncome.afterPurchase();
                  }
                  objIncome.adminRoll();
               }
            }
         } 
         else if (user_amount == totalCost) {
            money += user_amount;
            System.out.println("Thank You ");
            System.out.println("You are Welcome., ");
            CustomerPurchased();
            objIncome.GudownStockChange();

            if (Main.adminNumber == 1) {
               if ((adminworks != 0) && (adminworks != 3)) {
                  System.out.println("Gudowncheck" + SuperMarketIncome.gudowncheck);
                  objIncome.GudownStorage();
                  if (SuperMarketIncome.gudowncheck == 1) {
                     objIncome.purchasingGoods();
                     objIncome.afterPurchase();
                  }
                  objIncome.adminRoll();
               }
            } else {
               // System.out.println("Sorry you are not a admin");
            }

         } else if (user_amount < totalCost) {
            System.out.println("Sorry your cash is less then the total amount");
            returningCustomer();
            return;
         }
      }
      // inputScanner.close();
   }

   void CustomerPurchased() {
      String customerProducts = "Here Your Products            : ";
      String customerProducts_Price = "This is your product Quantity : ";

      for (int l = 0; l < productsList.size(); l++) {
         customerProducts = customerProducts.concat(productsList.get(l) + ", ");
         customerProducts_Price = customerProducts_Price.concat(productQuantity.get(l) + ", ");
      }

      System.out.println(customerProducts);
      System.out.println(customerProducts_Price);
   }

   void printingBills() {
      System.out.println("------------------------------------------------");
      System.out.println("    Product Name   |   Product Qn   |   Price  |");
      System.out.println("------------------------------------------------");

      try {
         for (int k = 0; k < productsList.size(); k++) {
            System.out.printf(" %-3s ", "");
            System.out.printf(" %-19s ", productsList.get(k));
            System.out.printf(" %-12s ", productQuantity.get(k));
            System.out.printf(" %-1d ", singleProductCostlist.get(k) * productQuantity.get(k));
            System.out.println();
            System.out.println("------------------------------------------------");
         }

         System.out.printf("                   |     Total      |    " + totalCost + "   |");
         System.out.println();
         System.out.println("------------------------------------------------");
         // System.out.println(singleProductCostlist);
      } 
      catch (Exception e) {
         // TODO: handle exception
         System.out.println("The error is Wrong entered error");
      }
   }

   void valueChange() {
      System.out.println("Enter the name of the Product you would like to change the price of the grocery");
      String changer = scanner.nextLine().toUpperCase().trim();
      int productChecker = 0;

      for (int i = 0; i < list.size(); i++) {
         if (list.get(i).get(0).equals(changer)) {
            System.out.println("Enter the New Price of the Product");
            int changePrice = scanner.nextInt();
            scanner.nextLine();
            list.get(i).set(2, changePrice + "");
            productChecker = 1;
            break;
         }
      }

      if (productChecker == 0) {
         System.out.println("Sorry this product is not in our Gudown");
         valueChange();
      }

      // System.out.println(list);

      try {
         FileWriter userFile = new FileWriter("Gudown.txt");

         for (int n = 0; n < list.size(); n++) {
            for (int m = 0; m < 3; m++) {
               userFile.write(list.get(n).get(m) + ",");
            }
            userFile.write("\n");
         }
         userFile.close();
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("There will be no error");
      }
   }

   void Store() {
      int index = 0;

      try {
         String path = "Gudown.txt";
         FileReader file = new FileReader(path);
         Scanner scanning = new Scanner(file);

         while (scanning.hasNextLine()) {
            ArrayList < String > mylist = new ArrayList < > ();
            String str = scanning.nextLine();
            String[] arrofstr = str.split(",");

            for (int i = 0; i < arrofstr.length; i++) {
               mylist.add(arrofstr[i]);
            }
            list.add(mylist);
            index++;
         }

         scanning.close();
      } 
      catch (Exception e) {
         System.out.println("hello all this is my error " + e);
      }
   }

   void stateStore() {
      try {
         String filename = "product_states.txt";
         FileReader filePath = new FileReader(filename);
         Scanner scan = new Scanner(filePath);

         while (scan.hasNextLine()) {
            String Str = scan.nextLine();
            String[] arrStrings = Str.split(",");

            marketStatesProducts.add(arrStrings[0]);
            marketStatesProductsQN.add(Integer.parseInt(arrStrings[1]));
         }
         scan.close();
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println(e);
      }
   }

   int consolidation(String name, int quan) {
      int customer_buy_quantity = quan;

      for (int i = 0; i < list.size(); i++) {
         if (list.get(i).get(0).equals(name)) {
            if (Integer.parseInt(list.get(i).get(1)) >= customer_buy_quantity) {
               singleProductCostlist.add(Integer.parseInt(list.get(i).get(2)));
               // System.out.println("Before price " + totalCost);
               totalCost = totalCost + customer_buy_quantity * Integer.parseInt(list.get(i).get(2));
               // System.out.println("After price " +totalCost);
               return totalCost;
            } 
            else {
               checkBounty = 1;
               return totalCost;
            }
         }
      }
      return totalCost;
   }

   static ArrayList < String > marketStatesProducts = new ArrayList < > ();
   static ArrayList < Integer > marketStatesProductsQN = new ArrayList < > ();

   // ADDING THE VALUES FROM THE PRODUCT QUANTITY ARRAY LIST FOR MONTHLsY STATES
   ArrayList < String > statesList = new ArrayList < > ();
   ArrayList < Integer > statesQNList = new ArrayList < > ();

   void monthlyStates() {
      statesList.clear();
      statesQNList.clear();
      statesList.addAll(productsList);
      statesQNList.addAll(productQuantity);
      ArrayList < String > highlyPurchaseList = new ArrayList < > ();
      ArrayList < Integer > highlyPurchaseQuantity = new ArrayList < > ();

      String stateProductName = "";
      int statesQuantity = 0;

      highlyPurchaseList.clear();
      highlyPurchaseQuantity.clear();

      for (int n = 0; n < list.size(); n++) {
         for (int m = 0; m < statesList.size(); m++) {
            if (list.get(n).get(0).equalsIgnoreCase(statesList.get(m))) {
               statesQuantity += statesQNList.get(m);
               stateProductName = list.get(n).get(0);
               highlyPurchaseList.add(stateProductName);
               highlyPurchaseQuantity.add(statesQuantity);
               statesQuantity = 0;
               break;
            }
         }
      }
      statesList.clear();
      statesQNList.clear();

      int indexing = 0;

      ArrayList < String > highlyPurchaseList2 = new ArrayList < > ();
      ArrayList < Integer > highlyPurchaseQuantity2 = new ArrayList < > ();

      while (0 < highlyPurchaseList.size()) {
         int highestvalue = Collections.max(highlyPurchaseQuantity);
         indexing = highlyPurchaseQuantity.indexOf(highestvalue);

         highlyPurchaseList2.add(highlyPurchaseList.get(indexing));
         highlyPurchaseQuantity2.add(highlyPurchaseQuantity.get(indexing));

         highlyPurchaseList.remove(indexing);
         highlyPurchaseQuantity.remove(indexing);
      }

      // int iteratable = 0;

      for (int y = 0; y < highlyPurchaseList2.size(); y++) {

         if (marketStatesProducts.contains(highlyPurchaseList2.get(y))) {
            int existProductindex = marketStatesProducts.indexOf(highlyPurchaseList2.get(y));
            int existingProductQn = marketStatesProductsQN.get(existProductindex);
            existingProductQn += highlyPurchaseQuantity2.get(y);
            marketStatesProductsQN.set(existProductindex, existingProductQn);
         } else if (!marketStatesProducts.contains(highlyPurchaseList2.get(y))) {

            int ind = 0;
            ind = Collections.binarySearch(marketStatesProductsQN, highlyPurchaseQuantity2.get(y));
            if (ind < 0) {
               ind = -ind - 1;
            }
            marketStatesProducts.add(ind, highlyPurchaseList2.get(y));
            marketStatesProductsQN.add(ind, highlyPurchaseQuantity2.get(y));
         }
      }

      try {
         FileWriter userFile = new FileWriter("product_states.txt");
         int move = marketStatesProducts.size() - 1;

         for (int n = 0; n < marketStatesProducts.size(); n++) {
            userFile.write(marketStatesProducts.get(move) + "," + marketStatesProductsQN.get(move));
            move--;
            userFile.write("\n");
         }
         userFile.close();
      } 
      catch (Exception e) {
         // TODO: handle exception
         System.out.println("There will be no error" + e);
      }
   }

   void printMonthlyStates() {
      ArrayList < String > printnames = new ArrayList < > ();
      ArrayList < Integer > printQn = new ArrayList < > ();

      ArrayList < String > printnames2 = new ArrayList < > ();
      ArrayList < Integer > printQn2 = new ArrayList < > ();

      // System.out.println("Marktet " + marketStatesProducts);
      // System.out.println(marketStatesProductsQN);
      printnames.addAll(marketStatesProducts);
      printQn.addAll(marketStatesProductsQN);

      int Index = 0;
      while (0 < printnames.size()) {
         int highestvalue = Collections.max(printQn);
         Index = printQn.indexOf(highestvalue);

         printnames2.add(printnames.get(Index));
         printQn2.add(printQn.get(Index));

         printnames.remove(Index);
         printQn.remove(Index);
      }

      // System.out.println(printnames2);
      // System.out.println(printQn2);

      try {
         if (printnames2.size() > 0) {
            System.out.println("----------------------------------------");
            System.out.println("           Product Statistics           ");
            System.out.println("----------------------------------------");
            System.out.println("|    Product Name   |   Purchased QN   |");
            System.out.println("----------------------------------------");

            for (int k = 0; k < printnames2.size(); k++) {
               System.out.printf(" %-3s ", "");
               System.out.printf(" %-19s ", printnames2.get(k));
               System.out.printf(" %-12s ", printQn2.get(k));
               System.out.println();
               System.out.println("----------------------------------------");
            }
         } 
         else {
            System.out.println();
            System.out.println("------------------------------------------------------");
            System.out.println("|     Sorry there is no Statistics were recorded     |");
            System.out.println("------------------------------------------------------");
            System.out.println();
         }
      } 
      catch (Exception e) {
         // TODO: handle exception
         System.out.println("The error is Wrong entered error" + e);
      }
   }

}

class SuperMarketIncome extends SuperMarket {
   static int gudowncheck = 0; // LESS THAN 5 QN IN GUDOWN
   static ArrayList < ArrayList < String >> purchaseList = new ArrayList < > ();

   void GudownStockChange() {
      int remaining_goods = 0;

      for (int i = 0; i < list.size(); i++) {
         for (int j = 0; j < productsList.size(); j++) {
            if (list.get(i).get(0).equals(productsList.get(j))) {
               remaining_goods = productQuantity.get(j);
               remaining_goods = Integer.parseInt(list.get(i).get(1)) - remaining_goods;
               list.get(i).set(1, remaining_goods + "");
               break;
            }
         }
      }

      try {
         FileWriter userFile = new FileWriter("Gudown.txt");

         // rewriting the quantity of product in text file after purchasing
         for (int n = 0; n < list.size(); n++) {
            for (int m = 0; m < 3; m++) {
               userFile.write(list.get(n).get(m) + ",");
            }
            userFile.write("\n");
         }
         userFile.close();
      } 
      catch (Exception e) {
         // TODO: handle exception
         System.out.println("There will be filewriter error" + e);
      }
   }

   void GudownStorage() {
      int iterater = 1;
      String requiredProduct = "";
      String requiredProductQuantity = "";

      for (int m = 0; m < list.size(); m++) {
         if (Integer.parseInt(list.get(m).get(iterater)) <= 5) {
            StorageName.add(list.get(m).get(0));
            StorageQuantity.add(Integer.parseInt(list.get(m).get(1)));
            gudowncheck = 1;
         }
      }

      if (gudowncheck == 1) {
         for (int n = 0; n < StorageName.size(); n++) {
            requiredProduct = requiredProduct.concat(StorageName.get(n) + ", ");
            requiredProductQuantity = requiredProductQuantity.concat(StorageQuantity.get(n) + ", ");
         }

         System.out.println();
         System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ALERT~~~~~~~~~~~~~~~~~~~~~~~~");
         System.out.println("   Hi Chief Our Storage in our Gudown is less than 5   ");
         System.out.println("       We need to take the load from the Vendors       ");
         System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
         System.out.println();
         BuyingGoods();
         System.out.println();
         System.out.println("Chief, Our Required Products                      : " + requiredProduct);
         System.out.println("Chief, Our Required Products Remaining Quantities : " + requiredProductQuantity);
         System.out.println("We need to Refill our Godown");
         System.out.println();
      } 
      else {
         System.out.println("Nothing we need to buy chief we have enough of Grocery in our Gudown");
         BuyingGoods();
      }
   }

   void BuyingGoods() {

      try {
         String paths = "buying_Goods.txt";
         FileReader files = new FileReader(paths);
         Scanner scanner = new Scanner(files);

         while (scanner.hasNextLine()) {
            ArrayList < String > mylist2 = new ArrayList < > ();
            String str2 = scanner.nextLine();
            String[] arrofstr2 = str2.split(",");

            for (int i = 0; i < arrofstr2.length; i++) {
               mylist2.add(arrofstr2[i]);
            }
            purchaseList.add(mylist2);
         }

         if (gudowncheck == 1) {
            System.out.println("This is Vendors list :");
            System.out.println("   Product    |    Price   ");

            for (int n = 0; n < purchaseList.size(); n++) {
               System.out.print("   ");
               for (int m = 0; m < purchaseList.get(0).size(); m++) {
                  System.out.printf(" %-15s ", purchaseList.get(n).get(m));
               }
               System.out.println();
            }
         }
      } 
      catch (Exception e) {
         System.out.println(purchaseList);
         System.out.println("This is Error Handling in text file to list " + e);
      }

   }

   void purchasingGoods() {
      Scanner getData = new Scanner(System.in);

      System.out.println("Tell me all the grocery you are going to buy with comma");
      System.out.println("Our Gudown storage was just 100 so below under 100 in everything");

      System.out.println("Enter the names of goods ");
      String purchaseGroceryNames = getData.nextLine();
      String[] purchaseGroceryList = purchaseGroceryNames.split(",");
      int purchaseGroceryQuantity = 0;

      for (int i = 0; i < purchaseGroceryList.length; i++) {
         for (int j = 0; j < list.size(); j++) {
            if (purchaseGroceryList[i].toUpperCase().equals(list.get(j).get(0))) {
               System.out.println("Enter the quantity of goods you are going to buy " + purchaseGroceryList[i]);
               purchaseGroceryQuantity = getData.nextInt();
               int purchaseGroceryPrice = Integer.parseInt(purchaseList.get(j).get(1)) * purchaseGroceryQuantity;

               if (purchaseGroceryPrice <= money) {
                  int remaining_goods = Integer.parseInt(list.get(j).get(1));
                  purchaseGroceryQuantity = purchaseGroceryQuantity + remaining_goods;
                  list.get(j).set(1, purchaseGroceryQuantity + "");
                  money = money - (purchaseGroceryPrice);
                  System.out.println("Chief Money: " + money);
               } 
               else {
                  System.out.println("Sorry currently you dont have enough money ");
               }
            }
         }
      }
   }

   void afterPurchase() {
      try {
         FileWriter userFile = new FileWriter("Gudown.txt");

         // rewriting the quantity of product in text file after purchasing from the // vendors
         for (int n = 0; n < list.size(); n++) {
            for (int m = 0; m < 3; m++) {
               userFile.write(list.get(n).get(m) + ",");
            }
            userFile.write("\n");
         }
         userFile.close();
      } 
      catch (Exception e) {
         // TODO: handle exception
         System.out.println("There will be text write error" + e);
      }
   }

   void adminRoll() {
      Scanner nScanner = new Scanner(System.in);

      ArrayList < String > newStockList = new ArrayList < > (); // NEW STOCKS LIST WITH NAME AND QUAN AND PRICE
      ArrayList < String > StocksForVendors = new ArrayList < > (); // STOCK PRICE FOR VENDORS

      System.out.println("Your Money Chief: " + money);
      int addORnot = 1;
      int availableChecker = 0;

      if (addORnot == 1) {
         System.out.println("Tell me the name of the new products add");
         String newStock = nScanner.nextLine(); // NEW STOCK NAMES
         String[] newGoodsList = newStock.split(",");
         int newStockPrice = 0; // NEW STOCK PRICES
         int newStockQuantity = 0; // NEW STOCK QUANTITIES
         double StocktotalPrice = 0;

         for (int j = 0; j < list.size(); j++) {
            if (list.get(j).get(0).equals(newStock.toUpperCase())) {
               System.out.println("It already contains in the list chief");
               availableChecker = 1;
               break;
            }
         }

         if (availableChecker == 0) {
            for (int i = 0; i < newGoodsList.length; i++) {
               System.out.println("Enter the price of the product you are going to sell " + newGoodsList[i]);
               newStockPrice = nScanner.nextInt();
               nScanner.nextLine();
               System.out.println("Enter the quantity you are going to buy in the " + newGoodsList[i]);
               newStockQuantity = nScanner.nextInt();
               nScanner.nextLine();

               // PRICE FOR BUYING NEW PRODUCT
               StocktotalPrice = (newStockPrice * newStockQuantity);
               System.out.println(StocktotalPrice);

               newStockList.clear();
               if (money > StocktotalPrice) {
                  newStockList.add(newGoodsList[i].toUpperCase().trim());
                  newStockList.add(newStockQuantity + "");
                  newStockList.add(newStockPrice + "");

                  money = money - StocktotalPrice;
               } 
               else {
                  System.out.println("Sorry chief you dont have enough money");
                  adminRoll();
               }

               list.add(newStockList);
               StocksForVendors.add(newStock.toUpperCase().trim());

               StocksForVendors.add(((newStockPrice * 75) / 100) + "");
               purchaseList.add(StocksForVendors);

               // THIS IS FOR THE PRINTING THE MENU FROM PRODUCTSLIST ARRAYLIST
               // productsList.add(newStock.toUpperCase().trim());
               // productQuantity.add(newStockPrice);
               try {
                  FileWriter userFile = new FileWriter("Gudown.txt");

                  // rewriting the quantity of product in text file after adding new products to my godown
                  for (int n = 0; n < list.size(); n++) {
                     for (int m = 0; m < 3; m++) {
                        userFile.write(list.get(n).get(m) + ",");
                     }
                     userFile.write("\n");
                  }
                  userFile.close();
               } 
               catch (Exception e) {
                  // TODO: handle exception
                  System.out.println("There will be no error");
               }

               try {
                  FileWriter userFiles = new FileWriter("buying_Goods.txt");

                  // rewriting the quantity of product in text file after adding new products to my godown
                  for (int n = 0; n < purchaseList.size(); n++) {
                     for (int m = 0; m < 2; m++) {
                        userFiles.write(purchaseList.get(n).get(m) + ",");
                     }
                     userFiles.write("\n");
                  }
                  userFiles.close();
               } 
               catch (Exception e) {
                  // TODO: handle exception
                  System.out.println("There will be no error");
               }
            }
         }
      } 
      else {
         System.out.println("This product already containes in gudown");
         adminRoll();
      }
   }

   void adminStorePrint() {
      System.out.println(" ----------------------------------------------------------------------------");
      System.out.println(" |       Products       |       Q.nty       |     T.Stock    |     Price    |");
      System.out.println(" ----------------------------------------------------------------------------");

      for (int k = 0; k < list.size(); k++) {
         String price = list.get(k).get(2);
         System.out.printf(" %-5s ", "| ");
         System.out.printf(" %-20s ", list.get(k).get(0));
         System.out.printf(" %-18s ", 1 + " Kg/Pc");
         System.out.printf(" %-13s ", list.get(k).get(1));
         System.out.printf(" %-10s ", list.get(k).get(2) + " Rs");
         System.out.print("|");
         System.out.println();
      }
      System.out.println(" ----------------------------------------------------------------------------");
   }
}






// IMPORTANT NOTES
// [0] = ProductName
// [1] = ProductQuantity in Gudown
// [2] = ProductPrice per 1

// TO CHANGE THE VALUES IN THE MY 2D LIST CHANGING THE VALUES

// System.out.println(list.get(0).get(1));
// list.get(0).set(1, "55");
// System.out.println(list.get(0).get(1));

// TO REWRITE THE VALUES IN THE TEXT FILE USING FILE WRITER

// FileWriter usFileWriter = new FileWriter("Gudown2.txt");
// for (int n=0; n<20; n++) {
// for (int m=0; m<3; m++) {
// usFileWriter.write(list.get(n).get(m)+",");
// System.out.println(list.get(n).get(m));
// }
// usFileWriter.write("\n");
// }
// usFileWriter.close();

// 2D ARRAYLIST CREATING SYNTAX

// ArrayList <ArrayList<String>> list =new ArrayList <>();

// WORKABLE TRY AND CATCH
// try {
// FileWriter userFile = new FileWriter("Gudown.txt");

// for (int n=0; n<20; n++) {
// for (int m=0; m<3; m++) {
// userFile.write(list.get(n).get(m)+",");
// // System.out.print(list.get(n).get(m)+",");
// }
// // System.out.println("\n");
// userFile.write("\n");
// }
// userFile.close();

// } catch (Exception e) {
// // TODO: handle exception
// System.out.println("There will be no error");
// }

// try {
// FileWriter userFile = new FileWriter("Gudown.txt");

// for (int n=0; n<20; n++) {
// for (int m=0; m<3; m++) {
// userFile.write(list.get(n).get(m)+",");
// // System.out.print(list.get(n).get(m)+",");
// }
// // System.out.println("\n");
// userFile.write("\n");
// }
// userFile.close();

// } catch (Exception e) {
// // TODO: handle exception
// System.out.println("There will be no error");
// }

// THIS IS VERY NEEDED WELCOMING METHOD KEEP IT SAFE

// void welcoming() {
// Scanner iScanner = new Scanner(System.in);
// System.out.println("May I help you?");
// System.out.println("Yes or No");
// String YesorNo = iScanner.nextLine();
// System.out.println("is this working yesorno: " + YesorNo);
// checkStorage();
// iScanner.close();
// }

// PURCHASE GOODS

// void purchasingGoods(){
// Scanner getData = new Scanner(System.in);

// System.out.println("Tell me all the grocery you are going to buy");
// System.out.println("Our Gudown storage was just 100 so below under 100 in
// everything");

// System.out.println("Enter the names of the goods ");

// // if (getData.hasNextInt()) {
// int grocery = getData.nextInt();
// System.out.println("this is working " + grocery);
// // }
// // else {
// System.out.println("Invalid input. Please enter a valid integer.");
// // }

// getData.close();
// }

// remaining_goods wanted IMPORTANT

// for (int i=0; i<productsList.size(); i++) {
// System.out.println("produlslist size" + productsList.size());
// for (int j=0; j<list.size(); j++) {
// if (productsList.get(i).equals(list.get(j).get(0))) {
// remaining_goods = productQuantity.get(i);
// remaining_goods = Integer.parseInt(list.get(i).get(1)) - remaining_goods;
// list.get(i).set(1, remaining_goods + "");
// System.out.println(productQuantity + "the correct quantity");
// System.out.println(productsList + "the correct product");
// System.out.println(remaining_goods);
// break;
// }
// }
// }
// System.out.println(list);

// void PrintMenu() {
// System.out.println("Arun Super Market");
// System.out.println("---------------------------------------------------------------------");
// System.out.println("| S.No | Products | Price | Q.nty |");
// System.out.println("---------------------------------------------------------------------");
// System.out.println("| 1 | RICE | 40 Rs | 1Kg |");
// System.out.println("| 2 | WHEAT | 25 Rs | 1Kg |");
// System.out.println("| 3 | SUGAR | 50 Rs | 1Kg |");
// System.out.println("| 4 | BISCUIT | 20 Rs | 1Pcs |");
// System.out.println("| 5 | CHOCOLATE | 10 Rs | 1Pcs |");
// System.out.println("| 6 | BREAD | 30 Rs | 1Pcs |");
// System.out.println("| 7 | EGG | 5 Rs | 1Pcs |");
// System.out.println("| 8 | SALT | 20 Rs | 1Kg |");
// System.out.println("| 9 | SOAP | 20 Rs | 1Pcs |");
// System.out.println("| 10 | SHAMPOO | 5 Rs | 1Pcs |");
// System.out.println("| 11 | TOOTHPASTE | 30 Rs | 1Pcs |");
// System.out.println("| 12 | CHIPS | 20 Rs | 1Pcs |");
// System.out.println("| 13 | HONEY | 80 Rs | 1Pcs |");
// System.out.println("| 14 | PEPSI | 40 Rs | 1Pcs |");
// System.out.println("| 15 | BUTTER | 90 Rs | 1Kg |");
// System.out.println("| 16 | JAM | 40 Rs | 1Pcs |");
// System.out.println("| 17 | SAUCE | 90 Rs | 1Pcs |");
// System.out.println("| 18 | APPLE | 30 Rs | 1Pcs |");
// System.out.println("| 19 | BANANA | 10 Rs | 1Pcs |");
// System.out.println("| 20 | TOMATO | 30 Rs | 1Kg |");
// System.out.println("---------------------------------------------------------------------");
// System.out.println();
// System.out.println("Welcome ,,.");
// }

// CALLING THE ALL METHODS INSIDE THE CHECKSTORAGE
// System.out.println("just cool");

// if (Main.adminNumber == 1) {
// objIncome.GudownStorage();
// }

// if (SuperMarketIncome.gudowncheck == 1) {
// objIncome.purchasingGoods();
// }
// objIncome.afterPurchase();
// if (Main.adminNumber == 1) {
// objIncome.adminRoll();
// }
// else {
// System.out.println("Sorry you are not a admin");
// }

// switch (adminworks) {
// case 1:
// // objMarketIncome.BuyingGoods();
// // objMarketIncome.purchasingGoods();
// // objMarketIncome.afterPurchase();
// // objMarketIncome.printer();
// // moneyWrite();
// break;

// case 2:
// // objMarketIncome.adminRoll();
// // objMarketIncome.printer();
// // moneyWrite();
// break;

// case 3:
// // intro();
// // moneyWrite();
// break;
// default:
// break;
// }

    // void printer() {
    // System.out.println("
    // ------------------------------------------------------------------------");
    // System.out.println(" | Products | Q.nty | Price | Quan |");
    // System.out.println("
    // ------------------------------------------------------------------------");

    // for (int k = 0; k < list.size(); k++) {
    // String price = list.get(k).get(2);
    // System.out.printf(" %-5s ", "| ");
    // System.out.printf(" %-21s ", list.get(k).get(0));
    // System.out.printf(" %-17s ", 1 + " Kg/Pc");
    // System.out.printf("%-8s", list.get(k).get(1));
    // System.out.printf(" %-7s ", list.get(k).get(2) + " Rs");
    // System.out.print("|");
    // System.out.println();
    // }
    // System.out.println("
    // ----------------------------------------------------------");
    // }


// ADDING THE STATES CORRECTLY IN THE ARRAYLIST TO PRINT THE REQUIRED PRODUCT (DEMO, JUST REFERENCE)

// String defineProductString = highlyPurchaseList.get(k);
// for (int j=0; j<marketStatesProducts.size(); j++) {
//    if (marketStatesProducts.get(j).equalsIgnoreCase(defineProductString)) {
//       int existingProductIndex = marketStatesProducts.indexOf(defineProductString);
//    }
// }


// IMPORTANT

// for (int n=0; n<highlyPurchaseList.size(); n++) {
//             System.out.println(highlyPurchaseList);
//             System.out.println(highlyPurchaseQuantity);

//             int highestvalue = Collections.max(highlyPurchaseQuantity);
//             indexing = highlyPurchaseQuantity.indexOf(highestvalue);

//          for (int k=0; k<highlyPurchaseList.size(); k++) {
//             if (!marketStatesProducts.contains(highlyPurchaseList.get(indexing))) {
//                for (int j=0; j<marketStatesProducts.size(); j++) {
//                   if (marketStatesProductsQN.get(j) < highlyPurchaseQuantity.get(indexing)) {
//                      marketStatesProducts.add(j, highlyPurchaseList.get(indexing));
//                      marketStatesProductsQN.add(j, highlyPurchaseQuantity.get(indexing));
//                      // marketStatesProducts.add(highlyPurchaseList.get(indexing));
//                      // marketStatesProductsQN.add(highlyPurchaseQuantity.get(indexing));
//                   }
//                }
//                System.out.println("Marktet list 0" + marketStatesProducts);
//                System.out.println("Marktet list Price 0" + marketStatesProductsQN);
               
//             }
//             else {
//                int existingProductIndex = marketStatesProducts.indexOf(highlyPurchaseList.get(indexing));
//                int existingProductQn = marketStatesProductsQN.get(existingProductIndex);
//                existingProductQn += highlyPurchaseQuantity.get(indexing);
//                marketStatesProductsQN.set(existingProductIndex, existingProductQn);
//             }
//          }
//          System.out.println("Marktet list 2 " + marketStatesProducts);
//          System.out.println("Marktet list Price 2 " + marketStatesProductsQN);

//          if (!marketStatesProducts.contains(highlyPurchaseList.get(indexing))) {
//             System.out.println("is this correcly working");
//          }

//          highlyPurchaseList.remove(indexing);
//          highlyPurchaseQuantity.remove(indexing);
//          }

// for (int n=0; n<highlyPurchaseList2.size(); n++) {
            //    // if (ind < 0) {
            //    //    ind = -ind -1;
            //    // }