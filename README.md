# Advance AI Project
## Title: Identity Document Masking System 신분증 마스킹 시스템
This application was developed for the 2023 Artificial Intelligence Convergence Talent Development Project at Chonnam National University, Gwangju, South Korea.
### Effect and method
This Android app achieves automated detection and anonymization of sensitive details in Korean ID cards, such as names, addresses, identification numbers, and optionally, facial features. The user-friendly interface allows customization and manual review of redacted content. Developed in Android Studio, the app utilizes Optical Character Recognition (OCR) and face masking algorithms, leveraging Flask and Ngrok for secure communication with a local server. The server handles facial and text redaction, returning anonymized data to the app. This project demonstrates the seamless integration of mobile tech, image processing, and web services for a robust and user-friendly identity document anonymization solution. Refer to the figure below for a sample of the mobile application user flow.

![image](images/face_redaction.png)

### To Run...
1. Install [android studio](https://developer.android.com/studio) and its latest depencies
2. Install [flask](https://flask.palletsprojects.com/en/3.0.x/installation/) on your system
3. Clone this repository to android studio (be sure android studio is sync properly and all gradle required dependencies are up to date and compatible)
4. Extract the directory Identity-Document-Masking-System/flask_python to a separate directory on your system (keep it out of the android studio files) and install all paddleocr related dependencies. Please refer to [this repository](https://github.com/yunwoong7/korean_ocr_using_paddleOCR).
5. Install [Ngrok](https://ngrok.com/download)
6. Using your system terminal, navigate to your flask_python directory and run file `python web_api`
7. Also, run Ngrok and copy the provided Ngrok forwarding link (Excluding "-> http://localhost:5000") to the specified <domain> section of `app/src/main/res/xml/network_security_config.xml`
8. Copy the same link to the URL sections of `app/src/main/java/com/example/ai_projectapp/Redaction.java`
9. Run `app` in android studio (make sure your emulator or physical device has been set up and running)
