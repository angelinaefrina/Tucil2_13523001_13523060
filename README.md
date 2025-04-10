# Tucil2_13523001_13523060

## IF2211 Strategi Algoritma
Tucil 2 Stima Kompresi Gambar Dengan Metode Quadtree 

Dipersiapkan oleh:

| Nama                          | NIM      |
|:-----------------------------:|:--------:|
| Wardatul Khoiroh              | 13523001 |
| Angelina Efrina Prahastaputri | 13523060 |
## Penjelasan Singkat Program
<p align="justify"> Algoritma divide and conquer adalah algoritma yang menggunakan pendekatan solusi dengan membagi persoalan yang akan diselesaikan menjadi persoalan yang lebih kecil atau subpersoalan sehingga lebih mudah dipecahkan dan independen. Setelah subpersoalan tersebut diselesaikan, solusinya digabungkan kembali untuk memperoleh solusi keseluruhan.Program ini mengimplementasikan algoritma kompresi gambar menggunakan metode Quadtree. Metode ini membagi gambar menjadi blok-blok yang lebih kecil berdasarkan ambang batas error (threshold). Jika error dalam suatu blok melebihi threshold atau melebihi minimum block size, blok tersebut dibagi menjadi empat sub-blok hingga mencapai ukuran minimum atau error berada di bawah threshold. Hasil kompresi disimpan sebagai gambar baru, dan proses kompresi divisualisasikan dalam bentuk GIF animasi yang menunjukkan hasil kompresi pada setiap kedalaman Quadtree. Metode pengukuran error yang digunakan ada 4 yaitu variance, mean absolute deviation (MAD), max pixel difference (MPD), dan entropy.</p>

## Requirement Program
1. Bahasa Pemrograman : Java
2. Pustaka yang DIgunakan : java.awt.image, javax.imageio
3. Input : file gambar (.png, .jpg, dll)
4. Output : file gambar, file GIF animasi (.gif)

## Cara Menjalankan Program
1. Clone Repository Lakukan clone repository ke lokal menggunakan perintah:
```
git clone https://github.com/angelinaefrina/Tucil2_13523001_13523060.git
```
2. Jalankan perintah berikut di terminal:
```
javac -d bin src/strukturdata/Matriks.java src/strukturdata/Pixel.java src/InputOutputFile.java src/ErrorMeasurement.java src/ImageCompression.java src/QuadTreeNode.java src/Gif.java
```
```
java -cp bin ImageCompression
```
