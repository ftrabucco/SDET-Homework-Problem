import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class TestDictionary {

    private Dictionary dictionary;
    private DictionaryService dictService;
    List<String> dictionaryList;

    @Before
    public void setUp() {
        dictionary = new Dictionary();
        dictService = mock(DictionaryService.class);
        dictionary.setDictionaryService(dictService);
        when(dictService.getDictionary()).thenReturn(createDictionaryArray());
        dictionaryList = dictService.getDictionary();
    }

    /**
     * Create String list based on the Dictionary file (EnglishWords in this case) to mock the dictionary service
     * @return String list with the dictionary content
     */
    static List<String> createDictionaryArray() {
        List<String> listDictionary = new ArrayList<String>();
        BufferedReader reader;

        try {
            ClassLoader loader = TestDictionary.class.getClassLoader();
            File file = new File(loader.getResource("EnglishWords").getFile());
            reader = new BufferedReader(new FileReader(file));
            //reader = new BufferedReader(new FileReader("EnglishWords"));
            String line = reader.readLine();
            while (line != null) {
                listDictionary.add(line);
                line = reader.readLine(); // read next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listDictionary;
    }

    /**
     * Validate if given word exists in the dictionary to mock the isEnglishWord function
     * @param word
     * @return boolean based if the word was found in the dictionary
     */
    public boolean isThisEnglish(String word) {
        for (String w : dictionaryList) {
            if (w.equals(word.toLowerCase())) {
                System.out.println(word + " is a valid english word");
                return true;
            }
        }
        return false;
    }

    @Test
    public void validateComputadoraWord() {
        when(dictService.isEnglishWord("COMPUTADORA")).thenReturn(isThisEnglish("COMPUTADORA"));
        Assert.assertFalse("There is not \"COMPUTADORA\" word in this dictionary",dictionary.isEnglishWord("COMPUTADORA"));
    }

    @Test
    public void validateHouseWord() {
        when(dictService.isEnglishWord("HOUSE")).thenReturn(isThisEnglish("HOUSE"));
        Assert.assertTrue("There is not \"House\" word in this dictionary",dictionary.isEnglishWord("HOUSE"));
    }

    @Test
    public void validateNoWord() {
        when(dictService.isEnglishWord("NO")).thenReturn(isThisEnglish("NO"));
        Assert.assertTrue(dictionary.isEnglishWord("NO"));
        Assert.assertEquals(Arrays.asList("n", "no", "o", "on"),(dictionary.findPossibleWords("NO")));
    }

    @Test
    public void validateWorkingWord() {
        when(dictService.isEnglishWord("WORKING")).thenReturn(isThisEnglish("WORKING"));
        Assert.assertTrue("There is not \"WORKING\" word in this dictionary",dictionary.isEnglishWord("WORKING"));
        // Print all  possible words
        dictionary.findPossibleWords("WORKING");
    }
}



