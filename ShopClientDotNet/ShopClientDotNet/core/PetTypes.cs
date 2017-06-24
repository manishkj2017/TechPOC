using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopClientDotNet
{
    public class PetTypes : Attribute
    {
        public PetTypes(string name, int order, double price)
        {
            this.name = name;
            this.order = order;
            this.price = new Decimal(price);
        }
        private string name;
        private int order;
        private Decimal price;

        public string Name
        {
            get
            {
                return this.name;
            }
        }

        public int Order
        {
            get
            {
                return this.order;
            }
        }

        public Decimal Price
        {
            get
            {
                return this.price;
            }
        }
            

      
    }

    public enum Pets
    {
        [PetTypes("Dog", 0, 1000)] Dog,
        [PetTypes("Cat", 1, 400)] Cat,
        [PetTypes("Cow", 2, 6000)] Cow,
        [PetTypes("Donkey", 3, 3000)] Donkey
    
    }

    
}
