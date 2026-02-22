package kasirapp;

public class ItemBelanja {
    private Produk produk;
    private int qty;

    public ItemBelanja(Produk produk, int qty) {
        this.produk = produk;
        this.qty = qty;
    }

    public Produk getProduk() { return produk; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public double getSubtotal() { return produk.getHarga() * qty; }
}
