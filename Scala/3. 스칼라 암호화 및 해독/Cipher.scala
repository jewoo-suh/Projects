object Cipher{
  /** Bit-wise exclusive-or of two characters */
  def xor(a: Char, b: Char) : Char = (a.toInt ^ b.toInt).toChar

  /** Print ciphertext in octal */
  def showCipher(cipher: Array[Char]) =
    for(c <- cipher){ print(c/64); print(c%64/8); print(c%8); print(" ") }

  /** Read file into array */
  def readFile(fname: String) : Array[Char] = 
    scala.io.Source.fromFile(fname).toArray

  /** Read from stdin in a similar manner */
  def readStdin() = scala.io.Source.stdin.toArray

  /* ----- Functions below here need to be implemented ----- */

  /** Encrypt plain using key; can also be used for decryption */
  def encrypt(key: Array[Char], plain: Array[Char]) : Array[Char] = {
    var cipher = Array.ofDim[Char](plain.length);
    for(i <- 0 to ((plain.length)-1)) {
      cipher(i) = xor(plain(i), key(i%(key.length)))
    }
    return cipher;
  }

  def duplicateExist(arr: Array[Char]) : Boolean = {
    
    val K = arr.length
    
    for(j <- 1 to (K-1)) {
      if(arr.slice(0,j).sameElements(arr.slice((K-j),K))) {
        return true;
      }
    }
    
    /*
    for(i <- 0 to (arr.slice(0, 3).length)-1) print(arr.slice(0, 3)(i))
    println()
    for(i <- 0 to (arr.slice((K-3), K).length)-1) print(arr.slice((K-3), K)(i))
    println()
    return (arr.slice(0, 3).sameElements(arr.slice((K-3), K)));
    */

    return false;

  }

    def duplicateExistInt(arr: Array[Char]) : Int = {
    
    val K = arr.length
    
    for(j <- 2 to (K-2)) {
      if(arr.slice(0,j).sameElements(arr.slice((K-j),K))) {
        return j;
      }
    }
    
    /*
    for(i <- 0 to (arr.slice(0, 3).length)-1) print(arr.slice(0, 3)(i))
    println()
    for(i <- 0 to (arr.slice((K-3), K).length)-1) print(arr.slice((K-3), K)(i))
    println()
    return (arr.slice(0, 3).sameElements(arr.slice((K-3), K)));
    */

    return (K+1);

  }

  /** Try to decrypt ciphertext, using crib as a crib CRIB SHORTER*/
  def tryCrib(crib: Array[Char], ciphertext: Array[Char]) : Unit = {
    
    var text = Array.ofDim[Char](crib.length); 
    var length_of_key = 0;

    for(i <- 0 to ((ciphertext.length) - (crib.length)-1)) {

      for(j <- 0 to (crib.length)-1) {
        text(j) = ciphertext(i+j)
      }

      for(k <- 0 to ((text.length)-1)) {
        text(k) = xor(crib(k), text(k))
      }

      /*
      println("After XOR ")
      for(i <- 0 to (text.length)-1) print(text(i))
      println()
      */

      if(duplicateExistInt(text) != (text.length + 1)) {
        length_of_key = text.length - duplicateExistInt(text);

        /*
        println(length_of_key);
        println(i)
        println(i%length_of_key)
        println(length_of_key - i%length_of_key)
        */

        var move_index = i%length_of_key
        var Answer = text.slice(0, text.length - duplicateExistInt(text))
        Answer = Answer.takeRight(move_index) ++ Answer.dropRight(move_index)

        for(index <- 0 to Answer.length - 1) print(Answer(index)) 
        println();
        println(new String (encrypt(Answer, ciphertext)))

        //UDOLRUDO
      }      
    }

    println();
    
  }

  /** The first optional statistical test, to guess the length of the key */
  def crackKeyLen(ciphertext: Array[Char]) : Unit = {

    var counter = 0;

    for(shift <- 1 to 30) {
      print(shift + ": ")
      for(i <- 0 to ciphertext.length-shift-1) {
        if(ciphertext(i) == ciphertext(shift + i)) {
          counter += 1;
        }
      }
      println(counter)
      counter = 0;
    }

  }

  /** The second optional statistical test, to guess characters of the key. */
  def crackKey(klen: Int, ciphertext: Array[Char]) : Unit = {

    for(shift <- 1 to 30) {
      for(i <- 0 to ciphertext.length-shift*klen-1) {
        if(ciphertext(i) == ciphertext(shift*klen + i)) {
          var letter = xor(ciphertext(i), ' ');
          if((letter.toInt) >= 32 && letter.toInt <= 127) {
            println(i%klen + " " + letter);
          }
        }
      }
    }

  }

/** The main method just selects which piece of functionality to run */
  def main(args: Array[String]) = {
    // string to print if error occurs
    val errString = 
      "Usage: scala Cipher (-encrypt|-decrypt) key [file]\n"+
      "     | scala Cipher -crib crib [file]\n"+
      "     | scala Cipher -crackKeyLen [file]\n"+
      "     | scala Cipher -crackKey len [file]"

    // Get the plaintext, either from the file whose name appears in position
    // pos, or from standard input
    def getPlain(pos: Int) = 
      if(args.length==pos+1) readFile(args(pos)) else readStdin()

    // Check there are at least n arguments
    def checkNumArgs(n: Int) = if(args.length<n){println(errString); sys.exit()}

    // Parse the arguments, and call the appropriate function
    checkNumArgs(1)
    val command = args(0)
    if(command=="-encrypt" || command=="-decrypt"){
      checkNumArgs(2); val key = args(1).toArray; val plain = getPlain(2)
      print(new String (encrypt(key,plain)))
    }
    else if(command=="-crib"){
      checkNumArgs(2); val key = args(1).toArray; val plain = getPlain(2)
      tryCrib(key, plain)
    }
    else if(command=="-crackKeyLen"){
      checkNumArgs(1); val plain = getPlain(1)
      crackKeyLen(plain)
    }      
    else if(command=="-crackKey"){
      checkNumArgs(2); val klen = args(1).toInt; val plain = getPlain(2)
      crackKey(klen, plain)
    }
    else println(errString)
  }
}

/*

Example output from Scala code above:

jewoosuh@AERO:~/Downloads$ scala Cipher -encrypt RUDOLF santa
0%=l3;0.`f9!.?#r76&"!r8!o-f<03o./90d)#4r,=%5&8%<`f>:2*l
                                                    ==*E

jewoosuh@AERO:~/Downloads$ scala Cipher -encrypt RUDOLF santa | od -b
0000000 026 060 045 075 154 025 063 073 060 056 140 146 002 071 041 056
0000020 077 043 162 067 066 046 042 041 162 070 041 157 055 146 074 060
0000040 063 157 056 057 071 060 144 051 043 064 162 026 054 075 045 065
0000060 046 070 045 074 140 146 076 072 062 052 154 014 075 075 052 105
0000100

jewoosuh@AERO:~/Downloads$ od -b santa
0000000 104 145 141 162 040 123 141 156 164 141 054 040 120 154 145 141
0000020 163 145 040 142 162 151 156 147 040 155 145 040 141 040 156 145
0000040 167 040 142 151 153 145 040 146 157 162 040 103 150 162 151 163
0000060 164 155 141 163 054 040 154 157 166 145 040 112 157 150 156 012
0000100

jewoosuh@AERO:~/Downloads$ echo -n RUDOLF | od -b
0000000 122 125 104 117 114 106
0000006

jewoosuh@AERO:~/Downloads$ scala Cipher -encrypt RUDOLF santa > message
jewoosuh@AERO:~/Downloads$ scala Cipher -decrypt RUDOLF santa > message

jewoosuh@AERO:~/Downloads$ scala Cipher -crib Christmas message
RUDOLF
Dear Santa, Please bring me a new bike for Christmas, love John


jewoosuh@AERO:~/Downloads$ scala Cipher -crackKey 8 private2 | sort -n | uniq -c | awk '$1 > 6'
  40 0 H
  32 1 O
  19 2 G
  41 3 W
  12 4 A
  12 5 R
  18 6 T
  28 7 S

*/