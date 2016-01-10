# ImageGenerator
Generate the image with strange contours.
Works with OpenCV library.

#How to
To generate an image, you need to write the next code in your function:

```java
ImageGenerator imageGenerator = new ImageGenerator(new Size(1024, 768), 10);
Mat generatedImage = imageGenerator.generate();
```

#Results
In result, we will get something like this:
![Alt example](http://i.snag.gy/tgrTi.jpg)
