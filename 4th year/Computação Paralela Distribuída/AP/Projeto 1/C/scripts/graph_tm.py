import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages
import sys
from re import split

def main(args):
    
    fps = open("s.txt", "r")
    fpc = open("c.txt", "r")

    ss = []
    cs = []

    m_sizev = list(range(1,100))

    print(m_sizev)


    for line in fpc:
        elems = line.split(r',')
        print(elems.__len__())
        cs.append([float(elems[i]) for i in range(1, elems.__len__()-1)])

    for line in fps:
        elems = line.split(r',')
        ss.append([float(elems[i]) for i in range(1, elems.__len__()-1)])

    with PdfPages("Compare_Costs_Steps_MI.pdf") as export_file:
            plt.plot(m_sizev, cs[0],  color='r', label='50')
            plt.plot(m_sizev, cs[1],  color='g', label='100')
            plt.plot(m_sizev, cs[2],  color='b', label='200')
            plt.plot(m_sizev, cs[3],  color='m', label='500')
            plt.xlabel("Tamanho da matrix (centenas)")
            plt.ylabel("Custo (unidades)")
            plt.title("Custo Algoritmo SA")
            plt.grid(True)
            plt.legend()
            export_file.savefig()
            plt.savefig("images/COST_COMPARE_MI.png")
            plt.close()

            plt.plot(m_sizev, ss[0],  color='r', label='50')
            plt.plot(m_sizev, ss[1],  color='g', label='100')
            plt.plot(m_sizev, ss[2],  color='b', label='200')
            plt.plot(m_sizev, ss[3],  color='m', label='500')
            plt.xlabel("Tamanho da matrix (centenas)")
            plt.ylabel("Passos (unidades)")
            plt.title("Passos Algoritmo SA")
            plt.grid(True)
            plt.legend()
            export_file.savefig()
            plt.savefig("images/STEPS_COMPARE_MI.png")
            plt.close()

    

if __name__ == "__main__":
    main(sys.argv)



