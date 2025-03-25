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

# Phase 4: Task 2

### Example event log:
```
22:10:48: Loaded character templates from 'data/templates'.
22:10:48: Successfully initialized new image conversion instance with no specified filepath.
22:10:48: Successfully initialized conversion history.
22:10:52: Successfully read image file from 'data\images\greetings.png'.
22:10:52: Character 'h' extracted with confidence: 0.7370849847793579.
22:10:52: Character 'e' extracted with confidence: 0.8055446743965149.
22:10:52: Character 'l' extracted with confidence: 0.5227122902870178.
22:10:52: Character 'l' extracted with confidence: 0.522712230682373.
22:10:52: Character 'o' extracted with confidence: 0.8228448033332825.
22:10:52: Processed image file and successfully extracted text.
22:10:52: Added conversion instance to history.
22:10:52: Loaded character templates from 'data/templates'.
22:10:52: Successfully initialized new image conversion instance with no specified filepath.
22:10:58: Successfully read image file from 'data\images\example.png'.
22:10:58: Character 'e' extracted with confidence: 0.8241224884986877.
22:10:58: Character 'x' extracted with confidence: 0.8571233749389648.
22:10:58: Character 'a' extracted with confidence: 0.8568914532661438.
22:10:58: Character 'm' extracted with confidence: 0.8792242407798767.
22:10:58: Character 'p' extracted with confidence: 0.7742010951042175.
22:10:58: Character 'r' extracted with confidence: 0.5558117628097534.
22:10:58: Character 'e' extracted with confidence: 0.8186091184616089.
22:10:58: Processed image file and successfully extracted text.
22:10:58: Added conversion instance to history.
22:10:58: Loaded character templates from 'data/templates'.
22:10:58: Successfully initialized new image conversion instance with no specified filepath.
22:11:10: Successfully read image file from 'data\images\mystery_word.png'.
22:11:10: Character 'o' extracted with confidence: 0.8021669387817383.
22:11:10: Character 'r' extracted with confidence: 0.9112370610237122.
22:11:10: Character 'a' extracted with confidence: 0.8413073420524597.
22:11:10: Character 'n' extracted with confidence: 0.9153560996055603.
22:11:10: Character 'g' extracted with confidence: 0.8822821378707886.
22:11:10: Character 'e' extracted with confidence: 0.8520680069923401.
22:11:10: Processed image file and successfully extracted text.
22:11:10: Added conversion instance to history.
22:11:10: Loaded character templates from 'data/templates'.
22:11:10: Successfully initialized new image conversion instance with no specified filepath.
22:11:21: Successfully read image file from 'data\images\banana.png'.
22:11:21: Character 'b' extracted with confidence: 0.8052780032157898.
22:11:21: Character 'a' extracted with confidence: 0.7424792051315308.
22:11:21: Character 'n' extracted with confidence: 0.9217548966407776.
22:11:21: Character 'a' extracted with confidence: 0.7424792051315308.
22:11:21: Character 'n' extracted with confidence: 0.9100258946418762.
22:11:21: Character 'a' extracted with confidence: 0.8243897557258606.
22:11:21: Processed image file and successfully extracted text.
22:11:21: Added conversion instance to history.
22:11:21: Loaded character templates from 'data/templates'.
22:11:21: Successfully initialized new image conversion instance with no specified filepath.
22:11:26: Cleared conversion history.
22:11:35: Successfully read image file from 'data\images\cpsc.png'.
22:11:35: Character 'c' extracted with confidence: 0.796286940574646.
22:11:35: Character 'p' extracted with confidence: 0.9057980179786682.
22:11:35: Character 's' extracted with confidence: 0.8501687049865723.
22:11:35: Character 'c' extracted with confidence: 0.7205554246902466.
22:11:35: Processed image file and successfully extracted text.
22:11:35: Added conversion instance to history.
22:11:35: Loaded character templates from 'data/templates'.
22:11:35: Successfully initialized new image conversion instance with no specified filepath.
```