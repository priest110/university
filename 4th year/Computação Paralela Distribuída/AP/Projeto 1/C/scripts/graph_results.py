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
    INPUT_FILE  = args[1]
    OUTPUT_FILE = args[2]

    # get MAX_ITER and TEMP_MULTIPLIER
    stuff = INPUT_FILE.split("_")

    MAX_ITER = stuff[1]
    TEMP_MULTIPLIER = stuff[2]

    print(TEMP_MULTIPLIER)

    fp            = open(INPUT_FILE, 'r')
    m_sizev       = list(range(1,101))
    exec_time_r   = []
    exec_time_sa  = []
    exec_time_gr  = []
    avg_cost_r    = []
    avg_cost_sa   = []
    avg_cost_gr   = []
    avg_steps_r   = []
    avg_steps_sa  = []
    avg_steps_gr  = []
    speedup_sa_5k = []
    speedup_ro_5k = []
    speedup_gr_5k = []
    amdahl        = [1,2,4,8,16,32]
    auxS = []
    auxC = []


    L = []
    i = 0

    # This is probably dumb, I hate not having switches, but it is what it is
    for line in fp:
        elems = line.split(',')

        if i == 0 :
            no_threads   = int(elems[2])
            exec_time_r  = []
            exec_time_sa = []
            exec_time_gr = []
            avg_cost_r   = []
            avg_cost_sa  = []
            avg_cost_gr  = []
            avg_steps_r  = []
            avg_steps_sa = []
            avg_steps_gr = []
        if   elems[0] == "Rooms":
            exec_time_r .append(int(elems[4]))
            avg_cost_r  .append(float(elems[5]))
            avg_steps_r .append(float(elems[6]))
            if int(elems[1]) == 5000 : # TO CALCULATE SPEEDUP 
                speedup_ro_5k.append(int(elems[4]))
        elif elems[0] == "RoomsSA":
            exec_time_sa.append(int(elems[4]))
            avg_cost_sa .append(float(elems[5]))
            avg_steps_sa.append(float(elems[6]))
            if int(TEMP_MULTIPLIER) == 999 and no_threads == 1:
                auxC.append(float(elems[5]))
                auxS.append(float(elems[6]))
            if int(elems[1]) == 5000 : # TO CALCULATE SPEEDUP 
                speedup_sa_5k.append(int(elems[4]))
        elif elems[0] == "RoomsGreedy":
            exec_time_gr.append(int(elems[4]))
            avg_cost_gr .append(float(elems[5]))
            avg_steps_gr.append(float(elems[6]) * int(elems[1]))
            if int(elems[1]) == 5000 : # TO CALCULATE SPEEDUP 
                speedup_gr_5k.append(int(elems[4]))

        i += 1

        if i == 300:
            L.append((no_threads, 
              exec_time_r, exec_time_gr, exec_time_sa,
              avg_cost_r,  avg_cost_sa,  avg_cost_gr,
              avg_steps_r, avg_steps_sa, avg_steps_gr))
            i = 0
    

    true_speedup_sa_5k = [ speedup_sa_5k[0] / x for x in speedup_sa_5k ]
    true_speedup_ro_5k = [ speedup_ro_5k[0] / x for x in speedup_ro_5k ]
    true_speedup_gr_5k = [ speedup_gr_5k[0] / x for x in speedup_gr_5k ]

    if auxC.__len__() != 0:
        print("coiso")

        fps = open("s.txt", "a")
        fpc = open("c.txt", "a")

        for e in auxC:
            fpc.write(str(e) + ",")
        fpc.write("\n")    

        for e in auxS:
            fps.write(str(e) + ",")
        fps.write("\n")    
        


        fps.close()
        fpc.close()


    with PdfPages(OUTPUT_FILE) as export_file:
        # Plot exec time, cost and steps
        for (t,tr,tsa,tgr,cr,csa,cgr,sr,ssa,sgr) in L:
            # Plot Execution Time
            plt.plot(m_sizev, tr,  color='r', label='Rooms')
            plt.plot(m_sizev, tsa, color='b', label='RoomsSA')
            plt.plot(m_sizev, tgr, color='g', label='Greedy')
            plt.xlabel("Tamanho da matrix (centenas)")
            plt.ylabel("T_exec (us)")
            plt.title("Tempo de Execução do Algoritmo (" + str(t) + ")")
            plt.grid(True)
            plt.legend()
            export_file.savefig()
            plt.savefig("images/gr_texec_" + str(t) + "_" + MAX_ITER + "_" + TEMP_MULTIPLIER + ".png")
            plt.close()

            # Plot Average Cost
            plt.plot(m_sizev, cr,  color='r', label='Rooms')
            plt.plot(m_sizev, csa, color='b', label='RoomsSA')
            plt.plot(m_sizev, cgr, color='g', label='Greedy')
            plt.xlabel("Tamanho da matrix (centenas)")
            plt.ylabel("Custo (unidades) ")
            plt.title("Custo da Solução (" + str(t) + ")")
            plt.grid(True)
            plt.legend()
            export_file.savefig()            
            plt.savefig("images/gr_cost_" + str(t) + "_" + MAX_ITER + "_" + TEMP_MULTIPLIER + ".png")
            plt.close()

            # Plot Average Steps
            plt.plot(m_sizev, sr,  color='r', label='Rooms')
            plt.plot(m_sizev, ssa, color='b', label='RoomsSA')
            plt.plot(m_sizev, sgr, color='g', label='Greedy')
            plt.xlabel("Tamanho da matrix (centenas)")
            plt.ylabel("Nº Passos (unidades) ")
            plt.title("Passos para obter Solução (" + str(t) + ")")
            plt.grid(True)
            plt.legend()
            x1,x2,y1,y2 = plt.axis()  
            plt.axis((x1,x2,0,20000))
            export_file.savefig()
            plt.savefig("images/gr_steps_" + str(t) + "_" + MAX_ITER + "_" + TEMP_MULTIPLIER + ".png")
            plt.close()

        # Plot Speedup
        plt.plot(amdahl, true_speedup_ro_5k,  color='r', label='Rooms')
        plt.plot(amdahl, true_speedup_sa_5k,  color='g', label='RoomsSA')
        plt.plot(amdahl, true_speedup_gr_5k,  color='b', label='RoomsGreedy')
        plt.plot(amdahl, amdahl, color='b', label='Speedup Teórico')
        plt.xlabel("Número de Processos")
        plt.ylabel("Speedup")
        plt.title("Speedup")
        plt.grid(True)
        plt.legend()
        x1,x2,y1,y2 = plt.axis()
        plt.axis((x1,x2,0,16))
        plt.savefig("images/gr_speedup_" + str(t) + "_" + MAX_ITER + "_" + TEMP_MULTIPLIER + ".png")
        plt.close()




if __name__ == "__main__":
    signal.signal(signal.SIGINT, int_handle)
    main(sys.argv)



