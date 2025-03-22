# Image to Text Tool 

## What does it do?

This application will extract text from images containing plain text using simple *Optical Character Recognition* (OCR) logic. In its full form, users would be able to upload an image and copy the extracted text to use it elsewhere without manually typing it out. Within the confines of CPSC 210 term project restrictions, the user will be able to select a demo image from a set of pre-defined image files within the project data and continue the conversion process with it.

## Who will use it?

The tool is designed for:
- **Students** who need to extract text from images of *digital articles*, *pdf documents*, or other text-based materials.
- **Professionals** who need to digitalize *printed documents*.
- My own personal use.

## Why is this project of interest to you?

The idea first entered my mind back in November, 2024. While formatting my discrete mathematics assignment into LaTeX on PrairieLearn, I ran into many issues with losing my progress since the platform saved LaTeX entries as whole images, which could not be altered in fragments. Frankly, the frustration of repeatedly losing my work made me want to pull my hair out. Since most 'image to LaTeX' tools require a fee to use, the thought of creating my own deeply rooted in the back of my mind.

Recognizing the limitations of my current skill and knowledge, I will begin with something simpler: an application that extracts plain text of a certain font from images. After this term, I hope to take my application further and integrate support for other common fonts and eventually allow for conversion from formatted LaTeX to code.

## User Stories

- As a user, I want to be able to select an image from a predefined set of images files stored within the project data and add it as a new image conversion instance within my conversion history.
- As a user, I want to have the application recognize and gather plain text from within the image so that I can extract the text in a usuable format.
- As a user, I want to be able to view a list of the image conversions that I have executed previously, so I can keep track of all the images I’ve processed.
- As a user, I want to be able to remove an image conversion from my conversion history.
- As a user, I want to be able to select a certain image conversion and retrieve the image's file path and the extracted text from that instance.
- As a user, I want to be able to save my conversion history to file if I choose to do so
- As a user, I want to be able to be able to load my conversion history from file if I choose to do so

# Instructions for End User

### To run the application
- Run ```main()``` in ```ImageToTextGUI.java``` 

### Functionality
- You can add a conversion instance to the history list by selecting an image from the file path list and clicking the convert button
- You can extract the plain text gathered from the selected image by copying it from the conversion tab text area
- You can see the visual render of the chosen image in the conversion tab by selecting it from the file path list
- You can save the state of the application by clicking ```File``` in the menu bar and clicking ```Save```
- You can reload the state of the application by clicking ```File``` in the menu bar and clicking ```Load```