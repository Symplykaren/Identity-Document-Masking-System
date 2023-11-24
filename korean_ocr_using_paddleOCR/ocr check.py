
# from PIL import Image, ImageDraw
# from main import MyPaddleOCR
#
#
# def redact_ocr_results(image_path, ocr_results, include_keywords=None, exclude_keywords=None, confidence_threshold=0.9):
#     img = Image.open(image_path)
#     draw = ImageDraw.Draw(img)
#
#     for region in ocr_results:
#         region_coordinates = region[0]
#         x, y, w, h = [int(coord) for coord in [region_coordinates[0][0], region_coordinates[0][1],
#                                                region_coordinates[2][0] - region_coordinates[0][0],
#                                                region_coordinates[2][1] - region_coordinates[0][1]]]
#
#         # Extract text and confidence score from OCR result
#         text, confidence = region[1]
#
#         # Check if the region should be redacted based on criteria
#         if (
#                 (include_keywords is None or any(keyword in text for keyword in include_keywords)) and
#                 (exclude_keywords is None or all(keyword not in text for keyword in exclude_keywords)) and
#                 confidence >= confidence_threshold
#         ):
#             # Redact the region by drawing a red rectangle
#             draw.rectangle([x, y, x + w, y + h], fill="red")
#
#     # Save the redacted image
#     img.save("2redacted_image.jpg")
#
#
# # Specify the image path
# img_path = '/home/prlab/Desktop/pythonProject/korean_ocr_using_paddleOCR/assets/images/11.jpg'
#
# # Instantiate and run OCR
# ocr = MyPaddleOCR()
# ocr.run_ocr(img_path, debug=True)
# ocr_results = ocr.get_ocr_result()
#
# # Specify keywords to include or exclude
# include_keywords = ["최정자", "310817-7851031"]
# exclude_keywords = ["광주"]
#
# # Redact OCR results based on criteria
# redact_ocr_results(img_path, ocr_results, include_keywords=include_keywords, exclude_keywords=exclude_keywords)


# --------------------WORKS WELL--------------
# from PIL import Image, ImageDraw
# from main import MyPaddleOCR
#
#
# def redact_ocr_results(image_path, ocr_results):
#     img = Image.open(image_path)
#     draw = ImageDraw.Draw(img)
#
#     for region in ocr_results:
#         region_coordinates = region[0]
#         x, y, w, h = [int(coord) for coord in [region_coordinates[0][0], region_coordinates[0][1],
#                                                region_coordinates[2][0] - region_coordinates[0][0],
#                                                region_coordinates[2][1] - region_coordinates[0][1]]]
#
#         # Redact the region by drawing a red rectangle
#         draw.rectangle([x, y, x + w, y + h], fill="red")
#
#     # Save the redacted image
#     img.save("ocr and redacted_image.jpg")
#
#
# # Specify the image path
# img_path = '/home/prlab/Desktop/pythonProject/korean_ocr_using_paddleOCR/assets/images/11.jpg'
#
# # Instantiate and run OCR
# ocr = MyPaddleOCR()
# ocr.run_ocr(img_path, debug=True)
# ocr_results = ocr.get_ocr_result()
#
# # Redact OCR results
# redact_ocr_results(img_path, ocr_results)
