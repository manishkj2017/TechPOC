using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopClientDotNet
{
    class Pet
    {
        private string name;
        private Decimal price;
        private int tag;
        private int orderNumber;
        private int customerNumber;

        public int Tag
        {
            get { return this.tag; }
            set { this.tag = value; }
        }
        public int OrderNumber
        {
            get { return this.orderNumber; }
            set { this.orderNumber = value; }
        }
        public int CustomerNumber
        {
            get { return this.customerNumber; }
            set { this.customerNumber = value; }
        }
        public string Name
        {
            get { return this.name; }
            set { this.name = value; }
        }
        public Decimal Price
        {
            get { return this.price; }
            set { this.price = value; }
        }
   

    }
}
