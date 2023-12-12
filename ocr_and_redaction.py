from PIL import Image, ImageDraw
import cv2
import numpy as np
from main import MyPaddleOCR

def redact_text_regions(image_path, ocr_results, indices_to_redact):
    img = Image.open(image_path)
    draw = ImageDraw.Draw(img)

    # Read the image using OpenCV for blurring
    img_cv2 = cv2.imread(image_path)

    for index, region in enumerate(ocr_results):
        if index in indices_to_redact:
            region_coordinates = region[0]
            x, y, w, h = [int(coord) for coord in [region_coordinates[0][0], region_coordinates[0][1],
                                                   region_coordinates[2][0] - region_coordinates[0][0],
                                                   region_coordinates[2][1] - region_coordinates[0][1]]]

            # Redact the region by drawing a red rectangle
            draw.rectangle([x, y, x + w, y + h], fill="red")

            # Apply Gaussian blur to the specified region
            region_to_blur = img_cv2[y:y+h, x:x+w]
            blurred_region = cv2.GaussianBlur(region_to_blur, (15, 15), 0)  # Adjust the kernel size as needed
            img_cv2[y:y+h, x:x+w] = blurred_region

    # Save the redacted and blurred image
    img.save("OCR_redacted_image.jpg")
    cv2.imwrite("OCR_blurred_image.jpg", img_cv2)

    # Convert the redacted image to bytes for returning or further processing
    buffered = BytesIO()
    img.save(buffered, format="JPEG")
    return buffered.getvalue()

# Specify the image path
img_path = 'C:/Users/user/Desktop/flask_python/assets/images/0.jpg'

# Instantiate and run OCR
ocr = MyPaddleOCR()
ocr.run_ocr(img_path, debug=True)
ocr_results = ocr.get_ocr_result()

# Specify indices of regions to redact (0-indexed)
indices_to_redact = [1, 2, 3, 4, 5]

# Redact text regions and apply blurring
redacted_image_data = redact_text_regions(img_path, ocr_results, indices_to_redact)

# For testing purposes, you can save the processed image
with open("OCR_processed_image.jpg", "wb") as f:
    f.write(redacted_image_data)
