package yogur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IdIdentificator {
    //Proposed Map structure, int (block id) -> str,str , str is placeholder for direction in tree
    /*
    Las declaraciones duplicadas se detectan en insertaId, si ya existe id en el bloque current.
    Las ausentes en buscaId si Id no existe en ningún bloque.
    */
    private int currId; // gives blockId
    private ArrayList< HashMap<String,String>> idMap;

    /*
     Opens new
     */
    public IdIdentificator(){
        this.idMap = new ArrayList<>();
        this.currId = 0;
    }

    /*
    Starts a new block
     */
    void openBlock (){
        currId++;
        idMap.set(currId,new HashMap<>());
    }
    void closeBlock () {
        idMap.set(currId,new HashMap<>());
        currId--;
    }
    /*
    añade el identificador id al bloque en curso y la posición
    del árbol donde está su aparición de definición
    puntero
    */
    void insertId (String id,String pointer){
        HashMap<String,String> currMap = idMap.get(currId);
        //null check is preferable alas we don't store null
        if (currMap.get(id) != null ){
            System.out.println("Duplicate Id detected" + id);
            //here throw error
        } else{
            currMap.put(id,pointer);
            idMap.set(currId,currMap);

        }
    }

    /*
      busca la aparición de definición más interna para id

     */
    void buscaId (String id){

        boolean idExists = false;
        for (HashMap <String,String> hm : idMap) {
            if(hm.get(id) != null) {
                idExists = true;
                System.out.println(id+ " exists in block " + idMap.indexOf(hm));
            }
        }
        if (!idExists) {
            System.out.println(id + " doesn't exist in any block");
            //here throw error

        }
    }


}
