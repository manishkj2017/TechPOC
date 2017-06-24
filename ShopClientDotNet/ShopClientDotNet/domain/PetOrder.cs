using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopClientDotNet.domain
{
    public class PetOrder
    {
        private Decimal bidPrice;
        private string petType;
        private string status;
        private string customerName;
        private int customerNumber;
        private int orderNumber;
        private string statusReason;
        private string orderSource;
        private int petTag;

        public Decimal BidPrice { get { return this.bidPrice; } set { this.bidPrice = value; } }

        public string PetType { get { return this.petType; } set { this.petType = value; } }
        public string Status { get { return this.status; } set { this.status = value; } }
        public string CustomerName { get { return this.customerName; } set { this.customerName = value; } }
        public string StatusReason { get { return this.statusReason; } set { this.statusReason = value; } }
        public string OrderSource { get { return this.orderSource; } set { this.orderSource = value; } }

        public int CustomerNumber { get { return this.customerNumber; } set { this.customerNumber = value; } }
        public int OrderNumber { get { return this.orderNumber; } set { this.orderNumber = value; } }
        public int PetTag { get { return this.petTag; } set { this.petTag = value; } }
    }
}
