
import javax.swing.JOptionPane;

/*
 * Project: dbx-capture File: ImgurScript.java Author: Alex Kersten
 */

/*
 * NOTE: It is *extremely important* that this file exist in the default package
 * and not in com.dividebyxero.... Reason being, this will be executed as a
 * standalone .class file on the command line: java ImgurScript args This *won't
 * work* if this part of a package, because Java will expect a fully qualified
 * package name to be represented in the directory heirarchy as well, but this
 * file will just be sitting in .../scripts
 */
/**
 * This script will upload an image path on the command line to Imgur or save to
 * the desktop based on another command line flag. Separate from the rest of the
 * DBXCapture program in the sense that this class has its own main method and
 * should be invoked from the command line automatically by the script engine.
 *
 * @author Alex Kersten
 */
public class ImgurScript {

    /**
     * NOTE: Although this might be the first main method you see inspecting
     * this program, this *isn't* the main method for the program proper. This
     * is just the main method for the default upload script included with DBXC.
     * For the main method of the program, you'll have to go a few levels deeper
     * into com.dividebyxero.dbxcapture. See the NOTE at the top of this class
     * for why.
     *
     * This is the default script that is included with DBXCapture. The first
     * command line argument should be a path to an image to process. The second
     * command line argument is a 'true' or 'false' based on whether we are
     * operating in local mode ('true') in which case the image will be copied
     * to the desktop rather than uploaded to Imgur.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            JOptionPane.showMessageDialog(
                    null, "Not enough args passed to ImgurUpload script!",
                    "Upload script", JOptionPane.ERROR_MESSAGE);

            return;
        }

        JOptionPane.showMessageDialog(
                null,
                "DEBUG: Hello from the ImgurUpload script!\nImage path was: "
                + args[0] + "\nLocalmode is " + args[1], "Upload script",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
