/**
 * interface for Load Doc, Search Doc, Sum Doc.
 *  This sets the size for all tabs
 *  Does not need to implement just yet.
 *  Might need this for Listeners and Observers interactions.
 */
public interface TabbedPanes {
    final static int extraWindowWidth = 200;

    /** set Dimension for each panel, example
     * JPanel searchpan = new JPanel(){
     * public Dimension getPreferredSize () {
     * Dimension size = super.getPreferredSize();
     * size.width += extraWindowWidth;
     * return size;
     */
    void setDimension();

    void addComponents();




}
