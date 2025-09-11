# Image to Text Tool

A simple desktop application that extracts plain text from images using **Optical Character Recognition (OCR)**. Users can upload or select an image and convert its text into a usable format without manual typing.

---

## Features

- Extract plain text from images of a certain font.
- Maintain a history of all image conversions.
- Save and load conversion history to and from a file.
- Preview the selected image alongside the extracted text.

---

## Who is this for?

- **Students**: Extract text from digital articles, PDFs, or scanned notes.  
- **Professionals**: Digitize printed documents quickly.  
- **Anyone** who wants to avoid manually retyping text from images.  

---

## Motivation

The idea for this project came in November 2024 while I was formatting my discrete mathematics assignments in LaTeX on PrairieLearn. The platform saved my work as images rather than editable text, causing frequent loss of progress. Existing "image to LaTeX" tools often require a subscription, so I decided to create a simple, free solution for extracting text from images.  

This project starts with plain text recognition for specific fonts, but future plans include:  

- Supporting more fonts and text styles.  
- Converting formatted LaTeX images into editable code.  

---

## User Stories

- **Add a conversion**: Select an image from the predefined set and convert it to text.  
- **Extract text**: Retrieve the plain text from an image conversion.  
- **View history**: See a list of all images you've processed.  
- **Manage history**: Remove conversions you no longer need.  
- **Retrieve details**: Access the file path and extracted text for any conversion.  
- **Save/Load state**: Persist your conversion history across sessions.  

---

## Installation & Usage

1. Clone the repository:

   ```bash
   git clone <repo-url>
   ```
   
2. Open the project in your preferred Java IDE.

3. Run the application:
   ```
   main() in ImageToTextGUI.java
   ```
