# RoTan

Endpoint:<br>

**Lahan:**<br>
`Lahan.getSemua()` (static `ArrayList<Lahan>`)<br>
Mengembalikan data semua lahan yang dimiliki oleh pengguna.<br><br>
`Lahan.hapus(int)` (static `void`)<br>
Menghapus data lahan berdasarkan kode lahan, **semua data aktivitas pada data lahan yang dihapus juga akan ikut terhapus.**<br><br>
`Lahan.insert(Lahan)` (static `void`)<br>
Menambahkan data lahan baru.<br><br>

**Aktivitas:**<br>
`Aktivitas.getSemua()` (static `ArrayList<Aktivitas>`)<br>
Mengembalikan data semua aktivitas dari semua lahan yang dimiliki oleh pengguna.<br><br>
`Aktivitas.getAktivitasLahan(int)` (static `ArrayList<Aktivitas>`)<br>
Mengembalikan data semua aktivitas dari lahan tertentu berdasarkan kode lahan.<br><br>
`Aktivitas.getByKode(int)` (static `Aktivitas`)<br>
Mengembalikan aktivitas berdasarkan kode tertentu.<br><br>
`Aktivitas.insert(Aktivitas)` (static `void`)<br>
Menambahkan aktivitas baru.<br><br>
`Aktivitas.hapus(int)` (static `void`)<br>
Menghapus aktivitas berdasarkan kode aktivitas, **semua data fase pada data aktivitas yang dihapus juga akan ikut terhapus.**<br><br>
`Aktivitas.hapusAktivitasLahan(int)` (static `void`)<br>
Menghapus semua data aktivitas dari lahan tertentu.<br><br>
`Aktivitas.setSelesai(int kodeAktivitas)` (static `void`)<br>
Menyatakan selesai suatu aktivitas dengan kode tertentu, aktivitas yang telah selesai tidak bisa dihapus maupun ditambahkan fase baru.<br><br>

**Fase:**<br>
`FaseAktivitas.getSemua()` (static `ArrayList<FaseAktivitas>`)<br>
Mengembalikan semua data fase dari semua aktivitas.<br><br>
`FaseAktivitas.getFaseAktivitas(int kodeAktivitas)` (static `Stack<FaseAktivitas>`)<br>
Mengembalikan semua data fase dari aktivitas dengan kode tertentu (sudah diurutkan dari yang terlama ke yang terbaru).<br><br>
`FaseAktivitas.insert(FaseAktivitas)` (static `void`)<br>
Menambahkan fase baru pada suatu aktivitas.<br><br>
`FaseAktivitas.hapusFaseAktivitas(ArrayList<Integer> kodeAktivitas)` (static `void`)<br>
Menghapus semua data fase dari semua aktivitas berdasarkan kode aktivitas.<br><br>
`FaseAktivitas.rollbackFaseAktivitas(int kodeAktivitas)` (static `void`)<br>
Menghapus satu fase terakhir dari suatu aktivitas berdasarkan kode aktivitas.<br><br>

**Template Aktivitas:**<br>
`TemplateAktivitas.getSemua()` (static `ArrayList<Aktivitas>`)<br>
Mengembalikan data semua template aktivitas.<br><br>
`TemplateAktivitas.getByKode(int)` (static `Aktivitas`)<br>
Mengembalikan data template aktivitas berdasarkan kode tertentu.<br><br>
`TemplateAktivitas.insert(Aktivitas)` (static `void`)<br>
Menambahkan template aktivitas baru.<br><br>
`TemplateAktivitas.hapus(int)` (static `void`)<br>
Menghapus data template aktivitas<br><br>
