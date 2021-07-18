package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.util.Scanner;

public class  PromptFileReader {

    private File file;

    public PromptFileReader(String path) {
        this.file=readFile(path);
    }

    public File getFile() {
        return file;
    }

    private File readFile(String path) {

        File file = new File(path);

        while (!(file.exists() && file.isFile())) {
            System.out.println("FILE IS INVALID :(. PLEASE TRY AGAIN. \n");
            Scanner input = new Scanner(System.in);
            String newPath = input.nextLine();
            file=new File(newPath);
        }

        return  file;

    }


}
