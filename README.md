# ContactQR

ContactQR is an Android application that automates the process of creating a new contact record on your Android phone with the scanning and creation of QR codes. There are countless times where we have had to exchange our contact information with others, and repeating the same process every time can be extremely tedious. ContactQR allows users to: 

* Save a QR code holding their contact information. 
* Update their contact information and generate a new QR code. 
* Scan others ContactQR codes to automatically create a new contact record with their information.  

### Functionality 
ContactQR consists of a bottom navigation menu with options Code and Scan. The Code option handles the input of contact information and displays the QR code with the contact information. The QR code is only shown until the user inputs their contact information. The Scan option handles the scanning of QR codes and processes the information scanned into a new contact record. 

#### Code Option 
![Alt text](/Images/1.png?raw=true)

#### Scan Option 
![Alt text](/Images/2.png?raw=true)

### Installation
Currently the only method to install this application on an Android phone is by running the project through Android Studio. Follow these steps to install ContactQR:
1. Install Android Studio
2. Clone ContactQR GitHub repository
3. Open ContactQR repository folder in Android Studio
4. Connect Android phone to computer and run the project  
5. ContactQR is now installed on Android phone

### Built Using 
* [Barcode Scanner Zxing](https://github.com/dm77/barcodescanner/tree/master/zxing)
* [QR Code Generator](https://github.com/zxing/zxing/tree/master/core/src/main/java/com/google/zxing)
* [ZXing Android Embedded](https://github.com/journeyapps/zxing-android-embedded)
