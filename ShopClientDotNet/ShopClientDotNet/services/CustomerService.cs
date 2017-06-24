using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopClientDotNet
{
    public interface CustomerService
    {
        Decimal GetBidPrice(Pets pet);
        Pets GetPetChoice();
        ShopChannel GetCustomerInterfaceForPets(String shopChannel);
        String GetPetName(Pets pet);
    }
}
