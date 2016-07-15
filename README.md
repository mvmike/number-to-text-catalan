**Catalan number to text helper**
https://bintray.com/mvmike/maven/number-to-text-catalan

Converts number to text (Catalan language), including currency if needed. Decimals are optional and rounded up to 10^-2.

Static class. Usage example:


```java

NumberToText.numberToText(1, "euro")
     -->  un euro

NumberToText.numberToText(25.92, "euro")
     -->  vint-i-cinc euros amb noranta-dos cèntims

NumberToText.numberToText(133.50, "euro")
     -->  cent trenta-tres euros amb cinquanta cèntims

NumberToText.numberToText(755.13, "")
     -->  set-cents cinquanta-cinc amb tretze

NumberToText.numberToText(1714, "euro")
     -->  mil set-cents catorze euros

NumberToText.numberToText(55891.75513, "")
     -->  cinquanta-cinc mil vuit-cents noranta-un amb setanta-sis

NumberToText.numberToText(701060.1, "euro")
     -->  set-cents un mil seixanta euros amb deu cèntims
```
