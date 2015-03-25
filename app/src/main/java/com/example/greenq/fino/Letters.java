package com.example.greenq.fino;
import java.util.Random;
/**
 * Created by GreenQ on 01.03.2015.
 */
public class Letters {
    public String letter;
    public String leterImgLink;
    public String[] werbs =  {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М",
            "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я"};
    public int[] imgLinks = {,};


    public int GetLetterImgLink(int letter)
    {
        return 0;
    }

    public String[] GetRandomStrings(String[] actualLetters)
    {
        //int actualLettersIterator = 0;
        int randomPosition;
        Random random = new Random();
        String[] result = new String[13];
        for(int actualLettersIterator = 0; actualLettersIterator < actualLetters.length;)
        {
            result[getRandPosition(random, result)] = actualLetters[actualLettersIterator];
            actualLettersIterator++;
        }

        for(int i = 0; i<13; i++)
        {
            if(result[i] == null)
            {
                randomPosition = random.nextInt(33);
                result[i] = werbs[randomPosition];
            }
        }

        return result;
    }

    private int getRandPosition(Random random, String[] array)
    {
        int randomPosition;
        do {
            randomPosition = random.nextInt(13);
            if(array[randomPosition] == null)
                return randomPosition;
        } while(true);//while(array[randomPosition] == null);
    }
    //private boolean CheckLettersPresence(String letter)
    //{
        //for()
    //}

    public int GetLetterIndex(String letter)
    {
        for (int i = 0; i < werbs.length;)
        {
            if(werbs[i] == letter)
            {
                return i;
            }
            i++;
        }
        return 0;
    }



}
