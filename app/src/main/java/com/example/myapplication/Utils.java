package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";

    private static int ID = 0;
    private static int ORDER_ID = 0;

    private static final String ALL_ITEMS_KEY = "all_items";
    private static final String DB_NAME = "fake_database";
    private static final String CART_ITEMS_KEY = "cart_items";
    private static final Gson gson = new Gson();
    private static final Type groceryListType = new TypeToken<ArrayList<GroceryItem>>(){}.getType();

    public static void initSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null == allItems) {
            initAllItems(context);
        }
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private static void initAllItems(Context context) {
        ArrayList<GroceryItem> allItems = new ArrayList<>();
        GroceryItem eggBun = new GroceryItem(
                "Egg Bun",
                "A fluffy bun filled with egg.",
                "https://th.bing.com/th/id/OIP.4aiMne396d1eTq4UurtUBQHaIT?rs=1&pid=ImgDetMain",
                "Bakery",
                100,
                15
        );
        allItems.add(eggBun);

        GroceryItem hotDog = new GroceryItem(
                "Hot Dog",
                "Tasty hot dog sausages served with soft buns.",
                "https://th.bing.com/th/id/R.f92425fc6ed1701e750c84c9cb800d5b?rik=lhmtvijyVU2qPQ&pid=ImgRaw&r=0",
                "Bakery",
                350,
                25
        );
        allItems.add(hotDog);

        GroceryItem muffin = new GroceryItem(
                "Muffin",
                "Delicious, freshly baked muffins.",
                "https://th.bing.com/th/id/OIP.XD48KuzXdyAFco_puOUa3AHaLH?rs=1&pid=ImgDetMain",
                "Bakery",
                120,
                30
        );
        allItems.add(muffin);

        GroceryItem beef = new GroceryItem(
                "Beef",
                "Fresh, high-quality beef cuts.",
                "https://th.bing.com/th/id/R.9d1b1c098a3c700be65008110f6cec76?rik=6ZktyjO8lq2oew&pid=ImgRaw&r=0",
                "Meat",
                900,
                12
        );
        allItems.add(beef);

        GroceryItem fish = new GroceryItem(
                "Fish",
                "Fresh and healthy fish.",
                "https://th.bing.com/th/id/OIP.9dNGf9bx7BDYehXQuSZm0AHaGj?w=503&h=445&rs=1&pid=ImgDetMain",
                "Meat",
                1300,
                18
        );
        allItems.add(fish);

        GroceryItem pork = new GroceryItem(
                "Pork",
                "Tender cuts of pork.",
                "https://th.bing.com/th/id/OIP.AowArJxJUU74LYdS-MHTWAHaE6?rs=1&pid=ImgDetMain",
                "Meat",
                2500,
                22
        );
        allItems.add(pork);

        GroceryItem lamb = new GroceryItem(
                "Lamb",
                "Juicy, tender lamb.",
                "https://th.bing.com/th/id/OIP.K5IaziRu3XUeuM5M3d46ZAHaEK?rs=1&pid=ImgDetMain",
                "Meat",
                1500,
                10
        );
        allItems.add(lamb);

        GroceryItem banana = new GroceryItem(
                "Banana",
                "Sweet and ripe bananas.",
                "https://th.bing.com/th/id/R.6e4bfc31d619339f54f1d5d6d44b7438?rik=2wwXTLlylsSj1Q&pid=ImgRaw&r=0",
                "Fruits",
                350,
                30
        );
        allItems.add(banana);

        GroceryItem orange = new GroceryItem(
                "Orange",
                "Juicy, fresh oranges.",
                "https://th.bing.com/th/id/OIP.hSoEIs6rZKM52ikKacg6LAHaE8?w=940&h=627&rs=1&pid=ImgDetMain",
                "Fruits",
                200,
                25
        );
        allItems.add(orange);

        GroceryItem watermelon = new GroceryItem(
                "Watermelon",
                "Fresh and juicy watermelon.",
                "https://th.bing.com/th/id/R.e586988a32b5698e8225839e88b8ad1f?rik=cvicSAhE9hTN1A&riu=http%3a%2f%2fupload.wikimedia.org%2fwikipedia%2fcommons%2f4%2f40%2fWatermelons.jpg&ehk=%2fFmGHB1RY%2b8PN498L8ebbboMSLDX%2bZX4St%2faWMh8WBA%3d&risl=&pid=ImgRaw&r=0",
                "Fruits",
                150,
                20
        );
        allItems.add(watermelon);

        GroceryItem grapes = new GroceryItem(
                "Grapes",
                "Fresh and sweet grapes.",
                "https://www.essingtonfarm.co.uk/wp-content/uploads/2020/11/red-grapes.png",
                "Fruits",
                200,
                18
        );
        allItems.add(grapes);

        GroceryItem tomatoes = new GroceryItem(
                "Tomatoes",
                "Fresh, ripe tomatoes.",
                "https://www.tasteofhome.com/wp-content/uploads/2018/05/GettyImages-1043681048.jpg?w=1200",
                "Vegetables",
                200,
                30
        );
        allItems.add(tomatoes);

        GroceryItem carrots = new GroceryItem(
                "Carrots",
                "Crunchy and fresh carrots.",
                "https://th.bing.com/th/id/OIP.HJ1GybYAMddPVos5Ho2t9QHaFj?rs=1&pid=ImgDetMain",
                "Vegetables",
                400,
                35
        );
        allItems.add(carrots);

        GroceryItem bellPepper = new GroceryItem(
                "Bell Pepper",
                "Fresh, colorful bell peppers.",
                "https://th.bing.com/th/id/R.d0b78201e08e5c61da2c1d6da8a446d1?rik=mk5S4DEhYXguRw&riu=http%3a%2f%2fwww.1mg.com%2farticles%2fwp-content%2fuploads%2f2016%2f06%2frsz_18743425431_88d7b1682a_z-2.jpg&ehk=DXfrIWFedi08kMpIn3wlQChcoOrfI%2b2lx8Faq6BJ96A%3d&risl=&pid=ImgRaw&r=0",
                "Vegetables",
                200,
                25
        );
        allItems.add(bellPepper);

        GroceryItem onions = new GroceryItem(
                "Onions",
                "Fresh onions for cooking.",
                "https://th.bing.com/th/id/OIP.gVtmSE6LK51PNC-jiwU5ZgHaE?rs=1&pid=ImgDetMain",
                "Vegetables",
                300,
                40
        );
        allItems.add(onions);

        GroceryItem milk = new GroceryItem(
                "Milk",
                "Fresh, pure milk.",
                "https://www.morningfreshdairy.com/wp-content/uploads/2023/09/e02fde07d49ee258cc3f6d1b19207757.jpg",
                "Dairy",
                330,
                50
        );
        allItems.add(milk);

        GroceryItem butter = new GroceryItem(
                "Butter",
                "Rich and creamy butter.",
                "https://th.bing.com/th/id/OIP.tRrpX5Y-5w-l8Td0dByOzQHaFO?rs=1&pid=ImgDetMain",
                "Dairy",
                485,
                25
        );
        allItems.add(butter);

        GroceryItem yogurt = new GroceryItem(
                "Yogurt",
                "Creamy and smooth yogurt.",
                "https://teknikaplast.com.tr/slides/products/647ee4595ca58.jpg",
                "Dairy",
                200,
                30
        );
        allItems.add(yogurt);

        GroceryItem soda = new GroceryItem(
                "Soda",
                "Refreshing carbonated soda.",
                "https://i5.walmartimages.com/asr/eb1db01b-c70d-447a-a82a-47e1ee57d9d4.f76f5831889bd63510efb9ad15b15787.jpeg",
                "Beverages",
                105,
                40
        );
        allItems.add(soda);

        GroceryItem orangeJuice = new GroceryItem(
                "Orange Juice",
                "Freshly squeezed orange juice.",
                "https://th.bing.com/th/id/OIP.eopeDOHdnDRwILW_bmXuBAHaHa?rs=1&pid=ImgDetMain",
                "Beverages",
                310,
                30
        );
        allItems.add(orangeJuice);

        GroceryItem coffee = new GroceryItem(
                "Coffee",
                "Strong, rich coffee.",
                "https://www.titanui.com/wp-content/uploads/2023/09/20/Coffee-Pouch-Packaging-Design-Mockup-PSD.webp",
                "Beverages",
                400,
                25
        );
        allItems.add(coffee);

        GroceryItem greenTea = new GroceryItem(
                "Green Tea",
                "Refreshing green tea.",
                "https://studyfinds.org/wp-content/uploads/2023/04/Twinings-Green-Tea-e1681744946320.jpeg",
                "Beverages",
                280,
                35
        );
        allItems.add(greenTea);

        GroceryItem milkshake = new GroceryItem(
                "Milkshake",
                "Creamy and smooth milkshake.",
                "https://i5.walmartimages.com/seo/Pure-Protein-Strawberry-Milkshake-Complete-Protein-Shake-11-fl-oz-4-Ct_53fbc53f-9158-49d8-b629-64b612023e86.7527ddf8357a530de0c1d19223504c4c.jpeg",
                "Beverages",
                500,
                40
        );
        allItems.add(milkshake);

        GroceryItem diapers = new GroceryItem(
                "Diapers",
                "Soft, absorbent diapers.",
                "https://i5.walmartimages.com/seo/Pampers-Swaddlers-Diapers-Size-3-26-Count-Select-for-More-Options_a71a2f27-8083-43c4-97ad-ebedae80828f.0d2d24c3ebf7e27b834ad5e632901d8f.jpeg",
                "Baby Care",
                890,
                50
        );
        allItems.add(diapers);

        GroceryItem babyWipes = new GroceryItem(
                "Baby Wipes",
                "Gentle and soft baby wipes.",
                "https://down-ph.img.susercontent.com/file/0dd0fdedfa8f2fa0e78d2dcfe56754dc",
                "Baby Care",
                380,
                40
        );
        allItems.add(babyWipes);

        GroceryItem babyFood = new GroceryItem(
                "Baby Food",
                "Healthy baby food.",
                "https://i5.walmartimages.com/seo/Gerber-2nd-Foods-Mealtime-for-Baby-Baby-Food-Chicken-Gravy-2-5-oz-Jar_fc60da9d-4241-46d0-84ca-0c6842220f5d.f590c52b938b7f0bbd1f881a24a22b98.jpeg",
                "Baby Care",
                200,
                30
        );
        allItems.add(babyFood);

        GroceryItem babyShampoo = new GroceryItem(
                "Baby Shampoo",
                "Gentle baby shampoo.",
                "https://th.bing.com/th/id/R.d303e4e30039d075b886acee0cf4e1cb?rik=XzE6LAq08wQRGA&pid=ImgRaw&r=0",
                "Baby Care",
                590,
                25
        );
        allItems.add(babyShampoo);

        GroceryItem babyBottle = new GroceryItem(
                "Baby Bottle",
                "BPA-free baby bottle.",
                "https://i5.walmartimages.com/asr/37c6132e-92c5-45ae-8d79-113196735fe6.96a9c0a879edbf9d00c0cb1708cdba52.jpeg",
                "Baby Care",
                395,
                40
        );
        allItems.add(babyBottle);

        GroceryItem toothpaste = new GroceryItem(
                "Toothpaste",
                "Toothpaste with cavity protection.",
                "https://i5.walmartimages.com/seo/Colgate-Toothpaste-with-Cavity-Protection-Active-Fluoride-2-5-oz-1-Count-24-Packs-24-Total_63c056ca-6dd2-42af-a7d0-c535ec9d4523.9bd6451763409e4f58cbc171998b2ce9.jpeg",
                "Personal Care",
                200,
                50
        );
        allItems.add(toothpaste);

        GroceryItem toiletPaper = new GroceryItem(
                "Toilet Paper",
                "Soft and absorbent toilet paper.",
                "https://th.bing.com/th/id/R.d8234707992e28566ccda3e1291280ca?rik=4wxD8pwVVv64pQ&pid=ImgRaw&r=0",
                "Household Essentials",
                295,
                20
        );
        allItems.add(toiletPaper);

        GroceryItem detergent = new GroceryItem(
                "Detergent",
                "Effective laundry detergent.",
                "https://th.bing.com/th/id/R.e701421bf942fbaedcd840d91f2e954b?rik=aqhT22paMgN5RA&riu=http%3a%2f%2fwww.southernsavers.com%2fwp-content%2fuploads%2f2013%2f01%2fTide-Liquid-Detergent.jpg&ehk=XbhIa7gurr2l99WgbrPkGlA%2btKhzrxDVDINxvQH35T0%3d&risl=1&pid=ImgRaw&r=0",
                "Household Essentials",
                500,
                25
        );
        allItems.add(detergent);

        GroceryItem trashBags = new GroceryItem(
                "Trash Bags",
                "Durable and strong trash bags.",
                "https://i5.walmartimages.com/asr/855e3854-ece4-43a2-803f-75f83b6d2f41_2.7533c36cd3387bbb5a7142ac42162d34.jpeg",
                "Household Essentials",
                38,
                15
        );
        allItems.add(trashBags);

        GroceryItem cleaningSpray = new GroceryItem(
                "Cleaning Spray",
                "Effective cleaning spray for surfaces.",
                "https://m.media-amazon.com/images/I/71+CsG1Y2RL.jpg",
                "Household Essentials",
                290,
                30
        );
        allItems.add(cleaningSpray);

        GroceryItem dishwashingLiquid = new GroceryItem(
                "Dishwashing Liquid",
                "Effective dish soap for spotless dishes.",
                "https://th.bing.com/th/id/OIP.UGxEN5W00FNo8RGz9O-T-gHaHa?w=1000&h=1000&rs=1&pid=ImgDetMain",
                "Household Essentials",
                290,
                20
        );
        allItems.add(dishwashingLiquid);

        GroceryItem shampoo = new GroceryItem(
                "Shampoo",
                "Gentle shampoo for clean hair.",
                "https://th.bing.com/th/id/OIP.nAey0d43xJrSQmLFPScswQHaHa?rs=1&pid=ImgDetMain",
                "Personal Care",
                300,
                25
        );
        allItems.add(shampoo);

        GroceryItem soap = new GroceryItem(
                "Soap",
                "Mild soap for everyday use.",
                "https://www.mamavation.com/wp-content/uploads/2020/02/soap-body-wash-investigation-non-toxic-brands-13-scaled.jpg",
                "Personal Care",
                80,
                30
        );
        allItems.add(soap);

        GroceryItem lotion = new GroceryItem(
                "Lotion",
                "Moisturizing lotion for smooth skin.",
                "https://th.bing.com/th/id/OIP.Z47x0NxVEoWGbjhftMuj_gHaHa?rs=1&pid=ImgDetMain",
                "Personal Care",
                377,
                20
        );
        allItems.add(lotion);

        GroceryItem deodorant = new GroceryItem(
                "Deodorant",
                "Long-lasting deodorant for fresh underarms.",
                "https://th.bing.com/th/id/OIP.3plgEsOk-eHTQN3iBr-jgAHaHa?rs=1&pid=ImgDetMain",
                "Personal Care",
                205,
                30
        );
        allItems.add(deodorant);

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.apply();
    }

    public static ArrayList<GroceryItem> getAllItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        return gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
    }

    public static void changeRate(Context context, int itemId, int newRate) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: allItems) {
                if (i.getId() == itemId) {
                    i.setRate(newRate);
                    newItems.add(i);
                }else {
                    newItems.add(i);
                }
            }

            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.apply();
        }
    }

    public static void addReview(Context context, Review review) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: allItems) {
                if (i.getId() == review.getGroceryItemId()) {
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItems.add(i);
                }else {
                    newItems.add(i);
                }
            }

            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.apply();
        }
    }

    public static ArrayList<Review> getReviewById(Context context, int itemId) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            for (GroceryItem i: allItems) {
                if (i.getId() == itemId) {
                    return i.getReviews();
                }
            }
        }

        return null;
    }

    public static void addItemToCart (Context context, GroceryItem item) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), groceryListType);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItems.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
        editor.apply();
    }

    public static ArrayList<GroceryItem> getCartItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(CART_ITEMS_KEY, null);
        if (jsonString == null) {
            return new ArrayList<>();
        }
        return gson.fromJson(jsonString, groceryListType);
    }

    public static void clearCart(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.apply();
    }


    public static ArrayList<GroceryItem> searchForItems(Context context, String text) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item: allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    items.add(item);
                }

                String[] names = item.getName().split(" ");
                for (String name : names) {
                    if (text.equalsIgnoreCase(name)) {
                        boolean doesExist = false;

                        for (GroceryItem j : items) {
                            if (j.getId() == item.getId()) {
                                doesExist = true;
                                break;
                            }
                        }

                        if (!doesExist) {
                            items.add(item);
                        }
                    }
                }
            }

            return items;
        }

        return null;
    }

    public static ArrayList<String> getCategories(Context context) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<String> categories = new ArrayList<>();
            for (GroceryItem item: allItems) {
                boolean doesExist = false;
                for (String s: categories) {
                    if (item.getCategory().equals(s)) {
                        doesExist = true;
                        break;
                    }
                }

                if (!doesExist) {
                    categories.add(item.getCategory());
                }
            }

            return categories;
        }

        return null;
    }

    public static ArrayList<GroceryItem> getItemsByCategory (Context context, String category) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item: allItems) {
                if (item.getCategory().equals(category)) {
                    items.add(item);
                }
            }

            return items;
        }

        return null;
    }

    public static void deleteItemFromCart(Context context, GroceryItem item) {
        ArrayList<GroceryItem> cartItems = getCartItems(context);
        if (null != cartItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: cartItems) {
                if (i.getId() != item.getId()) {
                    newItems.add(i);
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY, gson.toJson(newItems));
            editor.apply();
        }
    }

    public static void clearCartItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.apply();
    }

    public static void increasePopularityPoint(Context context, GroceryItem item, int points) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: allItems) {
                if (i.getId() == item.getId()) {
                    i.setPopularityPoint(i.getPopularityPoint() + points);
                    newItems.add(i);
                }else {
                    newItems.add(i);
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            Gson gson =new Gson();
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.apply();
        }
    }

    public static void changeUserPoint (Context context, GroceryItem item, int points) {
        Log.d(TAG, "changeUserPoint: Attempting to add " + points + " to " + item.getName());
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: allItems) {
                if (i.getId() == item.getId()) {
                    i.setUserPoint(i.getUserPoint() + points);
                }

                newItems.add(i);
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.apply();
        }
    }

    public static int getID() {
        ID++;
        return ID;
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static String getLicenses() {
        String licenses = "";

        //Gson
        licenses += "Gson\n";
        licenses += "Copyright 2025 Lasindu Lakmina.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "    http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n\n" +
                "limitations under the License.\n\n";


        return licenses;
    }
}
