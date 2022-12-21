from re import split
import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages
import sys
import signal, os

if __name__ == "__main__":
    # The horrible output when CTRL+C was annoying me
    def int_handle(signum,frame):
        print()
        exit(0)
    signal.signal(signal.SIGINT, int_handle)

signal.signal(signal.SIGINT, int_handle)

def main(args):

    OMEGAS   = [ "optimal", 1.2, 1.4, 1.8 ]
    OM_NAMES = ["def", "12", "14", "18"]
    THREADS  = [1,2,4,8,16,32]
    SIZES    = [i for i in range(50, 501, 50)]

    t_execs = {}
    iters   = {}

    # do it for GS
    fp = open("output/output_gs.txt", "r")

    aux_t = []
    aux_i = []

    i = 0

    for line in fp:
        if i < 10:
            elems = line.split(",")
            aux_t.append(int(elems[3]))
            aux_i.append(int(elems[4]))
            i+=1
        else:
            break

    fp.close()

    t_execs["GS"] = aux_t
    iters["GS"]   = aux_i

    i = 0

    # do it for gsrb
    for t in THREADS:
        fp = open("output/output_gsrb_" + str(t) + ".txt", "r")
        aux_t = []
        aux_i = []
        for line in fp:
            if i < 10:
                elems = line.split(",")
                aux_t.append(int(elems[3]))
                aux_i.append(int(elems[4]))
                i += 1
            else:
                break
        i = 0
        t_execs["GSRB_" + str(t)] = aux_t
        iters["GSRB_" + str(t)]   = aux_i
        fp.close()

    name = 0 

    # do it for SORRB
    #for n in OM_NAMES:
    fp = open("output/output_sorrb_p2.txt", "r")
    aux_t = []
    aux_i = []
    thr = 1
    for line in fp:
        elems = line.split(",")
        if int(elems[1]) == thr:
            if i < 10:
                aux_t.append(int(elems[3]))
                aux_i.append(int(elems[4]))
                i += 1
            else:
                continue
        else:
            i = 0
            t_execs["SORRB_" + str(OMEGAS[name]) + "_" + str(thr)] = aux_t
            iters["SORRB_" + str(OMEGAS[name]) + "_" + str(thr)]   = aux_i
            aux_i = []
            aux_t = []
            thr = int(elems[1])
            if i < 10:
                aux_t.append(int(elems[3]))
                aux_i.append(int(elems[4]))
                i += 1
            else:
                continue
    t_execs["SORRB_" + str(OMEGAS[name]) + "_" + str(thr)] = aux_t
    iters["SORRB_" + str(OMEGAS[name]) + "_" + str(thr)]   = aux_i
    name += 1
        
    gsrb_initial  = t_execs["GSRB_1"][9]
    sorrb_initial = t_execs["SORRB_optimal_1"][9]

    print(sorrb_initial)

    speedup_gsrb  = []
    speedup_sorrb = []

    for thr in THREADS:
        speedup_gsrb.append(gsrb_initial / t_execs["GSRB_" + str(thr)][9])
        speedup_sorrb.append(sorrb_initial / t_execs["SORRB_optimal_" + str(thr)][9])

    
    t_sorrb_mat = [0.0354/1e-6, 0.2442/1e-6, 0.8458/1e-6, 2.1376/1e-6, 4.5480/1e-6]
    target_exec_time = [6433, 57785, 201641, 504161, 1016006]



    with PdfPages("graficos_da_pinta.pdf") as export_file:
        # SPEEDUP
        #plt.plot(THREADS, speedup_gsrb, color='r', label="GS RB")
        plt.plot(THREADS, speedup_sorrb, color='b', label="SOR RB")
        plt.plot(THREADS, THREADS, color="g", label="Speedup Teórico")
        plt.xlabel("Número de Threads")
        plt.ylabel("Speedup")
        plt.title("Speedup Obtido (2 nodos)")
        plt.grid(True)
        plt.legend()
        plt.ylim(0,12)
        plt.savefig("speedup.png")
        export_file.savefig()
        plt.close()
        # ITERAÇÕES
        print(iters["GS"])
        print(iters["GSRB_1"])
        print(iters["SORRB_optimal_1"])
        plt.plot(SIZES, iters["GS"], color='r', label="GS")
        plt.plot(SIZES, iters["GSRB_1"], color='b', label="GS RB")
        plt.plot(SIZES, iters["SORRB_optimal_1"], color="g", label="SOR RB")
        plt.xlabel("Tamanho da malha")
        plt.ylabel("Iterações")
        plt.title("Número de Iterações")
        plt.grid(True)
        plt.legend()
        plt.savefig("iters_all.png")
        plt.ylim(-1000,)
        export_file.savefig()
        plt.close()
        # VS MATLAB
        #plt.plot([100,200,300,400,500], target_exec_time, color='r', label="C")
        #plt.plot([100,200,300,400,500], t_sorrb_mat, color='b', label="MATLAB")
        #plt.xlabel("Tamanho da malha")
        #plt.ylabel("T_exec (us)")
        #plt.title("Comparação do Tempo de Execução C vs. MATLAB")
        #plt.grid(True)
        #plt.legend()
        #plt.savefig("cvm.png")
        #export_file.savefig()




if __name__ == "__main__":
    signal.signal(signal.SIGINT, int_handle)
    main(sys.argv)



