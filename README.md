# Cifrado con esteganografia

## Requerimientos

- java `sudo apt install default-jdk`
- ant `sudo  apt install ant`

## Ejecución

- `ant run`

## Como correr el jar

  ```bash
  bodhi@yasmin:~/Desktop/ProyectoCripto/esteganografia/build/jar$ java -jar Application.jar 

  ```

## Como crer un ejecutable 

- `ant jar`

## Steganografia

```
Mensaje: Brenda Yasmin
Bandera para guardar la longitud: 13

Representación binaria

bandera : 00001101
    B       r         e          n        d     a
01000010 01110010 01100101 01101110 01100100 01100001 

[espacio]
00100000 

    Y       a       s       m           i       n
01011001 01100001 01110011 01101101 01101001 01101110


110 bits (8 bits de la bandera + 102 del mensaje)

```

### Ocultar

1. Se obtiene la longitud mensaje.
2. Convertir la longitud a binario de 8 bits.
3. Se obtiene el LSB de cada pixel.
4. Se realiza una bandera donde los primeros 8 pixeles(se usa el bit menos significativo de cada uno) indican la longitud del mensaje.
5. Cada caracter del mensaje se conviete a un binario de 8 bits.
6. Cada bit del mensaje se guarda en un pixel en seguida de la bandera, cuando el mensaje completo se guarda, el resto de pixeles se deja igual.


### Mostrar

1. Se obtiene el LSB inverso de los primeros 8 pixeles.
2. Esos 8 bits se usan para construir un numero entero usado como bandera.
3. Los siguientes LSB de cada pixel se convierten en un caracter ascci en grupos de 8 bits.
4. Se concatenan hasta extraer el numero de caracteres que indica la bandera y concatenarlos en un String que es el mensaje contenido.
5. El resto de pixeles en la imagen se ignoran.
