String ruta= "C:\\Users\\Viera\\Desktop\\entradaA.txt"
File archivo = new File (ruta);
FileReader fr = new FileReader (archivo);
BufferedReader br = new BufferedReader(fr);
String linea = br.readLine();
System.out.println(linea);