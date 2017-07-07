## Catalan number to text helper

Converts number to text (Catalan language), including currency if needed. Decimals are optional and rounded up to 10^-2.

Static class. Usage example:

```java

NumberToText.get(1, "euro")
      -->  un euro

NumberToText.get(25.92, "euro")
     -->  vint-i-cinc euros amb noranta-dos cèntims

NumberToText.get(133.50, "euro")
     -->  cent trenta-tres euros amb cinquanta cèntims

NumberToText.get(755.13, "")
     -->  set-cents cinquanta-cinc amb tretze

NumberToText.get(1714, "euro")
     -->  mil set-cents catorze euros

NumberToText.get(55891.75513, "")
     -->  cinquanta-cinc mil vuit-cents noranta-un amb setanta-sis

NumberToText.get(701060.1, "euro")
     -->  set-cents un mil seixanta euros amb deu cèntims
```
