## PDF file creator

You can create a bunch of random letters as PDF files.

### Distort PDF files

You can imitate a PDF from scan or fax with ImageMagick's convert:

```
convert -density 150 letter.pdf -colorspace gray -linear-stretch 3.5%x10% -blur 0x1 -attenuate 0.25 +noise Gaussian -rotate 0.75 as-scanned.pdf
```

see [ImageMagick security policy](https://imagemagick.org/script/security-policy.php)

## Keyword driven image fetcher

You can fetch scaled down images for keywords from Pixabay.


## Create large images with ImageMagick

JPG images with large file sizes
```
180 MB -> convert -size 8000x8000 xc: +noise Random -quality 100% random.jpg
55 MB -> convert -size 4000x5000 xc: +noise Random -quality 100% random.jpg
25 MB -> convert -size 3000x3000 xc: +noise Random -quality 100% random.jpg
```

Just use other file suffixes to use different image encodings
```
convert -size 8000x8000 xc: +noise Random -quality 100% random.png

```