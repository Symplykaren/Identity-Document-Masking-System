from flask import Flask, request, send_file, jsonify
from io import BytesIO
import cv2
import numpy as np
import io
from PIL import Image, ImageDraw
from paddleocr import PaddleOCR

app = Flask(__name__)

ocr = PaddleOCR(use_angle_cls=True, lang='korean')

def perform_face_redaction(image_data):
    # Convert image data to numpy array
    nparr = np.frombuffer(image_data, np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)

    # Convert the image to grayscale
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # Load the face cascade
    face_cascade = cv2.CascadeClassifier('C:/Users/user/Desktop/flask_python/haarcascade_frontalface_alt.xml')

    # Detect faces in the image
    faces = face_cascade.detectMultiScale(gray, 1.1, 2)

    # Loop over all the detected faces
    for (x, y, w, h) in faces:
        # Extract the face region
        face_region = img[y:y+h, x:x+w]

        # Apply Gaussian blur to the face region
        face_region = cv2.GaussianBlur(face_region, (0, 0), 10)

        # Replace the face region with the blurred face
        img[y:y+h, x:x+w] = face_region

    # Encode the processed image back to bytes
    _, processed_image_data = cv2.imencode('.png', img)

    return processed_image_data.tobytes()

def redact_text_regions(image_data):
    try:
        # Convert image data to numpy array
        nparr = np.frombuffer(image_data, np.uint8)
        img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)

        # Perform OCR on the image
        ocr_results = ocr.ocr(img)

        # Loop through OCR results and redact text regions
        for index, region in enumerate(ocr_results):
            try:
                region_coordinates = region[0]
                x, y, w, h = [int(coord) for coord in [region_coordinates[0][0], region_coordinates[0][1],
                                                       region_coordinates[2][0] - region_coordinates[0][0],
                                                       region_coordinates[2][1] - region_coordinates[0][1]]]

                # Redact the text region by drawing a black rectangle
                cv2.rectangle(img, (x, y), (x + w, y + h), (0, 0, 0), thickness=cv2.FILLED)
            except IndexError as e:
                print(f"IndexError: {e}, index: {index}, region: {region}")

        # Encode the processed image back to bytes
        _, processed_image_data = cv2.imencode('.png', img)

        return processed_image_data.tobytes()

    except Exception as e:
        # Print the exception to the console for debugging
        print(f"Exception: {e}")
        return jsonify({'error': 'Error processing image'}), 500


@app.route('/face_redaction', methods=['POST'])
def face_redaction():
    try:
        # Receive image data from Android app
        image_data = request.get_data()

        # Perform face redaction
        processed_image_data = perform_face_redaction(image_data)

        # Return the processed image
        return send_file(io.BytesIO(processed_image_data), mimetype='image/png')

    except Exception as e:
        # Print the exception to the console for debugging
        print(f"Exception: {e}")
        return jsonify({'error': 'Error processing image'}), 500

@app.route('/text_redaction', methods=['POST'])
def text_redaction():
    try:
        # Receive image data from Android app
        image_data = request.get_data()

        # Perform text redaction
        processed_image_data = redact_text_regions(image_data)

        # Return the processed image
        return send_file(io.BytesIO(processed_image_data), mimetype='image/png')

    except Exception as e:
        # Print the exception to the console for debugging
        print(f"Exception: {e}")
        return jsonify({'error': 'Error processing image'}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)