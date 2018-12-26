package assignment1;
/* Name: Mustafain Ali Khan
 * ID: 260770776
 */
public class Message {
    
    public String message;
    public int lengthOfMessage;
    
    public Message (String m){
        message = m;
        lengthOfMessage = m.length();
        this.makeValid();
    }
    
    public Message (String m, boolean b){
        message = m;
        lengthOfMessage = m.length();
    }
    
    /**
     * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
     */
    public void makeValid(){
        char temp;
        String result="";
        
        for (int i=0 ; i< message.length() ; i++) {
            temp=message.charAt(i);
            if(Character.isLetter(temp)){
                if(temp>=65 && temp<= 90) {
                    /* if condition ensures that Upper Case letters are turned into lower case
                     by following line of code*/
                    temp= (char) (temp -'A'+'a');
                }
                result=result+temp;
            }
        }
        message= result;
        lengthOfMessage=message.length();
    }
    /**
     * prints the string message
     */
    public void print(){
        System.out.println(message);
    }
    
    /**
     * tests if two Messages are equal
     */
    public boolean equals(Message m){
        if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
            return true;
        }
        return false;
    }
    
    /**
     * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
     * @param key
     */
    public void caesarCipher(int key){
        // INSERT YOUR CODE HERE
        String result="";
        char temp;
        for(int i= 0 ; i< message.length() ; i++) {
            temp=message.charAt(i);
            temp= (char) ((temp - 'a' + key) % 26 + 'a');
            //to make sure that the result of the cipher is a valid letter
            if (temp >'z') {
                temp=(char)(temp-26);
            }else if(temp<'a') {
                temp=(char)(temp+26);
            }
            result=result+temp;
        }
        message=result;
        lengthOfMessage=message.length();
    }
    
    public void caesarDecipher(int key){
        this.caesarCipher(-1*key);
    }
    
    /**
     * caesarAnalysis breaks the Caesar cipher
     * you will implement the following algorithm :
     * - compute how often each letter appear in the message
     * - compute a shift (key) such that the letter that happens the most was originally an 'e'
     * - decipher the message using the key you have just computed
     */
    public void caesarAnalysis(){
        //index of the frequency table corresponds to each letter from a-z with a having 0 index value.
        int[] frequency = new int[26];
        char c;
        for(int i = 0; i < message.length() ; i++) {
            c=message.charAt(i);
            int index= c-'a';
            frequency[index]++;
        }
        int highest=frequency[0];
        int maxIndex = 0;
        int key;
        char high =0;
        for(int j = 0 ; j<frequency.length ; j++ ) {
            if(frequency[j]>=highest) {
                highest=frequency[j];
                maxIndex=j;
                //maxIndex corresponds to the index of letter which is most frequent in the message
            }
        }
        high= (char) (maxIndex + 'a');
        key= high -'e';
        //after calculating the key, caeserDecipher is used to Decipher the message
        caesarDecipher(key);
    }
    
    /**
     * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
     * @param key
     */
    
    public void vigenereCipher(int[] key) {
        int count=0;
        char temp;
        String result = "";
        for(int i =0; i<lengthOfMessage; i++) {
            
            if(count==key.length) {
                //repeat keys from start of key array after each complete cycle
                count=0;
            }
            temp=message.charAt(i);
            
            temp= (char) ((temp - 'a' + key[count]) % 26 + 'a');
            count++;
            if (temp>'z') {
                temp=(char)(temp-26);
            }else if(temp<'a') {
                temp=(char)(temp+26);
            }
            result=result+temp;
        }
        message=result;
        lengthOfMessage=message.length();
    }
    
    
    /**
     * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
     * @param key
     */
    public void vigenereDecipher (int[] key){
        
        for(int i = 0 ; i<key.length ; i++) {
            //each key becomes negative of original, in order to decipher message
            key[i]= -1* key[i];
        }
        this.vigenereCipher(key);
    }
    
    /**
     * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
     * @param key
     */
    public void transpositionCipher (int key){
        if(key<0){
            key=key*-1;
            /*since key equals columns of 2D matrice, negative key implies that columns are negative in
             direction which cannot be possible as matrice index starts from 0, this just reflects matrice
             in oppossite direction*/
        }
        int rows= (lengthOfMessage/key) +1;
        int count=0;
        char[][] array= new char[rows][key];
        for(int i=0 ; i < rows ; i++ ) {
            for(int j=0 ; j < key ; j++) {
                if( count < lengthOfMessage) {
                    array[i][j]=message.charAt(count);
                    count=count+1;
                }else {
                    array[i][j]='*';
                }
            }
        }
        String result="";
        for(int j=0 ; j< key ; j++ ) {
            for(int i=0 ; i < rows ; i++ ) {
                result=result+array[i][j];
            }
        }
        message=result;
        lengthOfMessage=message.length();
    }
    
    
    /**
     * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
     * @param key
     */
    public void transpositionDecipher (int key){
        // INSERT YOUR CODE HERE
        int columns= lengthOfMessage/key;
        int count = 0;
        char[][] array= new char[key][columns];
        for(int i=0 ; i < key ; i++ ) {
            
            for(int j=0 ; j < columns ; j++) {
                array[i][j]=message.charAt(count);
                count=count+1;
            }
        }
        String result= "";
        for(int j=0 ; j < columns ; j++ ) {
            for(int i=0 ; i < key ; i++) {
                if(array[i][j] != '*') {
                    result=result + array[i][j];
                    count=count+1;
                }
            }
        }
        message=result;
        lengthOfMessage=message.length();
    }
}
