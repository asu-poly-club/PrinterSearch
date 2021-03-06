package core.help;

import javax.swing.ImageIcon;

/**
 * A help panel that describes how to print search results.
 * 
 * @author  Marcinina Alvaran
 * @version 1.0
 * @see     HelpPanel
 * @see     HelpFrame
 */
@SuppressWarnings("serial")
public class PrintResultsHelp extends HelpPanel
{
    /**
     * Creates a "Print Results" help panel.
     */
    public PrintResultsHelp()
    {
        setTitle("Printing Search Results");
        setTutorialText();
    }

    /**
     * Adds instructions to panel about how to print search results.
     */
    protected void setTutorialText()
    {
        String iconPath1, iconPath2, iconPath3,
               step1A, step1B, step2, step3A, step3B;
        
        // Add step 1 : Click toolbar button
        step1A = "Open the print menu by pressing �Export� on the upper toolbar.";
        step1B = "NOTE: The table may take time to load.";
        iconPath1 = getIconPath("Print-S1.png");
        addStep(step1A);
        addStep(step1B);
        addStep(new ImageIcon(iconPath1));
        addSpacing();
        
        // Add step 2 : Click "Page Setup" tab
        step2 = "Click \"Page Setup\" tab on the upper area of the print dialog";
        iconPath2 = getIconPath("Print-S2.png");
        addStep(step2);
        addStep(new ImageIcon(iconPath2));
        addSpacing();
        
        // Add step 3 : Click toolbar button
        step3A = "Manually update page orientation to \"Landscape\"";
        step3B = "NOTE: For Macs, use \"Reverse Landscape\"";
        iconPath3 = getIconPath("Print-S3.png");
        addStep(step3A);
        addStep(step3B);
        addStep(new ImageIcon(iconPath3));
        addSpacing();
    }

}
