package Servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadLock {
    private ReentrantLock lock; // lock
    private Condition download; // Condição de espera para os downloads
    private int downloaders_critica; // Nº de downloaders na zona crítica
    private int downloaders_esperando; // Nº de downloaders em espera para entrar na secção crítica
    private int downloadByMesmo_max; // Nº máximo de downloads na secção crítica
    private int  downloadByMesmo_seguido; // Nº de leituras e escritas seguidas

    /**
     * Metodo construtor
     */
    public DownloadLock(int num_downloads){
        this.lock = new ReentrantLock();
        this.download = this.lock.newCondition();
        this.downloadByMesmo_max = num_downloads;
        this.downloaders_critica = 0;
        this.downloaders_esperando = 0;
        this.downloadByMesmo_seguido = 0;
    }

    /**
     * Lock de download
     */
    public void downloadLock() throws InterruptedException {
        this.lock.lock();

        downloaders_esperando++;
        int i = downloaders_esperando;
        while(downloaders_critica == downloadByMesmo_max)
            download.await();
        downloaders_esperando--;
        downloaders_critica++;

        this.lock.unlock();

    }

    /**
     * Unlock de download
     */
    public void downloadUnlock() throws InterruptedException {
        this.lock.lock();

        downloaders_critica--;
        download.signal(); //De forma a avisar que saiu um downloader da secção crítica

        this.lock.unlock();
    }

}
