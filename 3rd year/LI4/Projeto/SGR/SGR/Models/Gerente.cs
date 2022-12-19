using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace SGR.Models
{
    public partial class Gerente
    {
        public Gerente()
        {
            Fornecedor = new HashSet<Fornecedor>();
            Funcionario = new HashSet<Funcionario>();
            Reserva = new HashSet<Reserva>();
        }

        public int Id { get; set; }


        [DataType(DataType.EmailAddress)]
        [Required(ErrorMessage = "Por favor forneça o Email")]
        public string Email { get; set; }

        [DataType(DataType.Password)]
        [Required(ErrorMessage = "Por favor forneça a Password")]
        public string Password { get; set; }

        public virtual ICollection<Fornecedor> Fornecedor { get; set; }
        public virtual ICollection<Funcionario> Funcionario { get; set; }
        public virtual ICollection<Reserva> Reserva { get; set; }
    }
}
