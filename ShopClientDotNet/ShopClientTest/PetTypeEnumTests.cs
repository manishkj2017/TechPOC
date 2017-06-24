using System;
using System.Reflection;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ShopClientDotNet;

namespace ShopClientTest
{
    [TestClass]
    public class PetTypeEnumTests
    {
        [TestMethod]
        public void testPetChoiceGenerator()
        {
            Type pets = typeof(Pets);
            String[] petNames = Pets.GetNames(pets);
            
            CustomerService cs = new CustomerServiceImpl();
            Pets pet = cs.GetPetChoice();
            Console.WriteLine(pet.ToString());
            String expectedPet = pet.ToString();
            int index = Array.IndexOf(petNames, expectedPet);
            Console.WriteLine("index - " + index);
            Assert.IsTrue(Array.IndexOf(petNames, expectedPet) >= 0 );
        }

        [TestMethod]
        public void testPetBidPriceGenerator()
        {

            CustomerService cs = new CustomerServiceImpl();
            Pets pet = Pets.Cat;
            Decimal price = cs.GetBidPrice(pet);
            Console.WriteLine(price);

            Assert.IsNotNull(price);
        }
    }
}
