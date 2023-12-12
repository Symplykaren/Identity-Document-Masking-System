import cv2

# read the input image
img = cv2.imread('/home/prlab/Desktop/pythonProject/korean_ocr_using_paddleOCR/data/images/26.jpg')

# convert to grayscale of each frames
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

# read haarcascade to detect faces in input image
face_cascade = cv2.CascadeClassifier('/home/prlab/Desktop/pythonProject/korean_ocr_using_paddleOCR/face recognition/'
                                     'haarcascade/haarcascade_frontalface_alt.xml')

# detects faces in the input image
faces = face_cascade.detectMultiScale(gray, 1.1, 2)
print('Number of detected faces:', len(faces)) \
 \
    # loop over all the detected faces
for (x, y, w, h) in faces:
    # Extract the face region
    face_region = img[y:y+h, x:x+w]

    # Apply Gaussian blur to the face region
    face_region = cv2.GaussianBlur(face_region, (0, 0), 10)

    # Replace the face region with the blurred face
    img[y:y+h, x:x+w] = face_region

# Display an image in a window
cv2.imwrite('face_redaction_image.jpg', img)
# cv2.imshow('Face Detection', img)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
