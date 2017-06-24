using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace ShopClientDotNet
{
    
    public class CustomerServiceImpl : CustomerService
    {
        private Random bidPriceGenerator = new Random();
        private Random petChoiceGenerator = new Random();
        private WEBShopChannel webCustomer = new WEBShopChannel();

       
        public Decimal GetBidPrice(Pets pet)
        {
            int priceOffset = 2000; //this needs to move to property file later
            MemberInfo[] members = pet.GetType().GetMember(pet.ToString());
            var attrs = members[0].GetCustomAttributes(typeof(PetTypes), false);
            Decimal price = ((PetTypes)attrs[0]).Price;

            int bidPrice =  bidPriceGenerator.Next(Convert.ToInt32(price) + priceOffset);

            return new decimal(bidPrice);
        }

        public ShopChannel GetCustomerInterfaceForPets(string shopChannel)
        {
            switch(shopChannel)
            {
                case "WEB":
                    return webCustomer;

                default:
                    return null;
            }
        }

        public Pets GetPetChoice()
        {
            var type = typeof(Pets);
            int length = Enum.GetNames(type).Length;
            int petChoice = petChoiceGenerator.Next(length);
            return (Pets)Enum.ToObject(type, petChoice);
 
        }

        public string GetPetName(Pets pet)
        {
            MemberInfo[] members = pet.GetType().GetMember(pet.ToString());
            var attrs = members[0].GetCustomAttributes(typeof(PetTypes), false);
            string name = ((PetTypes)attrs[0]).Name;
            return name;
        }
    }
}
